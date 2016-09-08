package com.citi.rell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.citi.rell.HiveScriptExecutor;

/**
 * A Spring Batch Tasklet, which can be used to execute Hive scripts in a Job.
 * 
 * @author Vinod Singh
 */
public class HiveScriptTasklet implements Tasklet {

    private Log logger = LogFactory.getLog(getClass());
    private final String comment = "--";

    // Parameters to be added to the scriptFile file
    private Set<String> params;

    // File (full path) having Hive queries, which will be executed
    private String scriptFile;

    // A directory where complete script (including parameters) will be written
    // if this variable is null final script won't be written anywhere
    private String tempLocation;

    // Maximum no of retry. User can set this variable using the setter method. Default is Zero
    private int maxRetries = 0;

    /**
     * Build parameters. Sub-classes should override this method if they have additional parameters.
     * 
     * @return parameter string
     * @throws Exception sub-classes may throw an exception
     */
    public String getParameters() throws Exception {
        StringBuilder sb = new StringBuilder();

        for (String param : params) {
            sb.append(param).append("\n");
        }

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String stepName = chunkContext.getStepContext().getStepName();
        logger.info("\nExecuting HiveScriptTasklet [" + scriptFile + " - " + stepName
                + "]\n=======================================");

        // look for skip property. if true, skip step execution
        String skipProp = "skip." + stepName;
        if (System.getProperty(skipProp, "false").equals("true")) {
            logger.info(skipProp + " is true, hence skipping this step.");
            return RepeatStatus.FINISHED;
        }
        long startTime = System.currentTimeMillis();

        // Get parameters, read script file and concat them to create final script
        String params = getParameters();
        String fileContent = readFile(scriptFile);
        String finalQueryScript = params + fileContent;

        // Write final query to disk
        if (tempLocation != null) {
            String filePath = tempLocation + scriptFile.substring(scriptFile.lastIndexOf(File.separatorChar));
            logger.info("Writing final script to: " + filePath);

            // Writing final script at temporary location
            writeFinalScript(filePath, finalQueryScript);
        }

        // Execute all Hive queries available in the final script
        HiveScriptExecutor hiveExecutor = new HiveScriptExecutor();
        hiveExecutor.setMaxRetries(maxRetries);
        hiveExecutor.executeScript(finalQueryScript);

        long endTime = System.currentTimeMillis();
        logger.info("HiveScriptTasklet [" + scriptFile + " - " + stepName + "] completed in  " + (endTime - startTime) / 1000
                + " seconds.");
        return RepeatStatus.FINISHED;
    }

    /**
     * Write final script to a file on disk.
     * 
     * @param filePath path where final script to be written
     * @param content contents of the file
     * @throws IOException
     */
    private void writeFinalScript(String filePath, String content) throws IOException {
        BufferedWriter br = null;
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // play safe, create directory structure if missing

            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            br.write(content);
            br.flush();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /**
     * Read script file contents.
     * 
     * @param filePath full path of the file to be read
     * @return file content as String
     * @throws Exception
     */
    private String readFile(String filePath) throws Exception {
        logger.info("Reading script file: " + filePath);
        BufferedReader reader = null;
        StringBuilder qsb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                // Ignore comments
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

    // Setters
    public void setParams(Set<String> params) {
        this.params = params;
    }

    public Set<String> getParams() {
        return params;
    }

    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public String getScriptFile() {
        return scriptFile;
    }

    public void setTempLocation(String tempLocation) {
        this.tempLocation = tempLocation;
    }

    public String getTempLocation() {
        return tempLocation;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

}
