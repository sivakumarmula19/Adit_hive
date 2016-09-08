package com.citi.rell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.cli.CliDriver;
import org.apache.hadoop.hive.cli.CliSessionState;
import org.apache.hadoop.hive.conf.HiveConf; 
import org.apache.hadoop.hive.ql.Driver;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.apache.hadoop.hive.shims.ShimLoader;

/**
 * A utility class to execute a Hive script file / String programmatically. Throws an error whenever a query returns
 * non-zero status.
 */
public class HiveScriptExecutor {

    private Log logger = LogFactory.getLog(getClass());
    private final Configuration conf = new HiveConf(SessionState.class);
    private final String comment = "--";

    /**
     * Maximum no of retry. User can set this variable using the setter method. Default is Zero
     */
    private int maxRetries = 0;

    /**
     * User can use this method to get the current retry option.
     * 
     * @return
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * This method allows the user to set the no of retry options
     * 
     * @param numRetryOption
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public HiveScriptExecutor() {
        // default constructor
    }

    /**
     * Execute all queries available in the given file. Queries are separated by ;.
     * 
     * @param fileName full path of the script file.
     * @throws Exception if something goes wrong or query execution return code is non-zero
     */
    public void executeFile(String fileName) throws Exception {
        // read query file and execute the queries
        executeScript(readFile(fileName));
    }

    /**
     * Execute all queries available in the passed {@linkplain String}. Queries are separated by ;.
     * 
     * @param script hive queries
     * @throws Exception if something goes wrong or query execution return code is non-zero
     */
    public void executeScript(String script) throws Exception {
        // start session
        CliSessionState ss = new CliSessionState((HiveConf) conf);
        ss.in = System.in;
        try {
            ss.out = new PrintStream(System.out, true, "UTF-8");
            ss.err = new PrintStream(System.err, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        // Add hive auxiliary jars to Hadoop class path
        if (!ShimLoader.getHadoopShims().usesJobShell()) {
            // hadoop-20 and above - we need to augment classpath using hiveconf
            // components
            // see also: code in ExecDriver.java
            ClassLoader loader = conf.getClassLoader();
            String auxJars = HiveConf.getVar(conf, HiveConf.ConfVars.HIVEAUXJARS);
            if (StringUtils.isNotBlank(auxJars)) {
                loader = Utilities.addToClassPath(loader, StringUtils.split(auxJars, ","));
            }
            conf.setClassLoader(loader);
            Thread.currentThread().setContextClassLoader(loader);
        }

        SessionState.start(ss);

        // execute ${user.home}/.hiverc file
        new CliDriver().processInitFiles(ss);

        try {
            // process queries separated by ;
            String command = "";
            for (String oneCmd : script.split(";")) {

                if (StringUtils.endsWith(oneCmd, "\\")) {
                    command += StringUtils.chop(oneCmd) + ";";
                    continue;
                } else {
                    command += oneCmd;
                }
                if (StringUtils.isBlank(command)) {
                    continue;
                }

                /*
                 * logic for re executing the query in case of any failure
                 */
                int retriesRemaining = maxRetries;
                do {
                    try {
                        execute(command);
                    } catch (Exception E) {
                        // check for remaining retries
                        if (!(retriesRemaining > 0)) {
                            throw E;
                        }
                        retriesRemaining--; // decreasing the retry count
                        logger.info("Query execution failed. Retries remaining: " + retriesRemaining);
                        logger.info("Re-attempting-\n" + command);
                        Thread.sleep(2000); // wait for a while before re-attempt
                        continue; // continue to re-attempt
                    }
                    // break out of the loop on success
                    break;
                } while (true);
                command = ""; // reset command
            }
        } finally {
            CommandProcessorFactory.clean((HiveConf) conf); // do clean up
        }
    }

    /**
     * Execute Hive query.
     * 
     * @param command query string to be executed
     * @throws HiveException if something goes wrong or return status is non-zero
     */
    private void execute(String command) throws HiveException {
        int returnCode = 0;
        String errorMessage = "";
        String sqlState = null;

        String cmd_trimmed = command.trim();
        String[] tokens = cmd_trimmed.split("\\s+");
        String cmd_1 = cmd_trimmed.substring(tokens[0].length()).trim();

        logger.info("Running the query: " + command);

        try {
            CommandProcessor proc = CommandProcessorFactory.get(tokens[0], (HiveConf) conf);
            CommandProcessorResponse response = null;
            if (proc == null) {
                logger.warn("CommandProcessor is null.");
                return; // nothing to process
            }

            // execute the query
            if (proc instanceof Driver) {
                Driver driver = (Driver) proc;
                long start = System.currentTimeMillis();

                response = driver.run(command);

                long end = System.currentTimeMillis();
                if (end > start) {
                    double timeTaken = (end - start) / 1000.0;
                    logger.info("Time taken: " + timeTaken + " seconds", null);
                }
            } else { // execute non-query command
                response = proc.run(cmd_1);
            }
            returnCode = response.getResponseCode();
            sqlState = response.getSQLState();
            errorMessage = response.getErrorMessage();

            logger.info("returnCode: " + returnCode);
        } catch (Exception e) {
            throw new HiveException("Error running query", e);
        }

        // fail the process on non-zero return code
        if (returnCode != 0) {
            logger.error("SQLState: " + sqlState);
            logger.error("Error message: " + errorMessage);
            throw new HiveException("Query returned non-zero code: " + returnCode);
        }
    }

    // Read query file contents
    private String readFile(String fileName) throws Exception {
        logger.info("Reading query file: " + fileName);
        BufferedReader reader = null;
        StringBuilder qsb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                // ignore comments
                if (line.startsWith(comment)) {
                    continue;
                }

                qsb.append(line + "\n");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return qsb.toString();
    }

    public static void main(String[] args) throws Exception {
        HiveScriptExecutor executor = new HiveScriptExecutor();
        executor.executeFile(args[0]);
    }
}
