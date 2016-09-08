package com.citi.rell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import org.springframework.batch.core.step.tasklet.Tasklet;


import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Set;

public class JaarvisDynamicQueryTasklet implements Tasklet {

    private Log logger = LogFactory.getLog(getClass());

    private static final String COMMENT = "--";
    
    // Parameters to be added to the scriptFile file
    private Set<String> params;

    // File (full path) having Hive queries, which will be executed
    private String scriptFile;

    // a directory where complete script (including parameters) will be written
    // if this variable is null final script won't be written anywhere
    private String tempLocation;

    // Maximum no of retry. User can set this variable using the setter method. Default is Zero
    private int maxRetries = 0;

    // Replacement Value(it will decide what is the replacement for <PLACEHOLDER>)
    private String clickstream_input;
    private String all_tags_input;
    private String output_loaction;
    private String hive_db_name;
    private String visit_output;
    
    
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


    private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String stepName = chunkContext.getStepContext().getStepName();
        logger.info("\nExecuting  [" + scriptFile + " - " + stepName
                + "]\n=======================================");

        // look for skip property. if true, skip step execution
        String skipProp = "skip." + stepName;
        if ("true".equals(System.getProperty(skipProp, "false"))) {
            logger.info(skipProp + " is true, hence skipping this step.");
            return RepeatStatus.FINISHED;
        }
        long startTime = System.currentTimeMillis();

        // get parameters, read script file and concat them to create final script
        String params = getParameters();
        logger.info("\nParams in ProductOwnerDashboardDynamicQueryTasklet [" + params + "]\n");

        String fileContent = readFile(scriptFile);
		String replacedFileContent = fileContent
				.replace("${hiveconf:ALL_TAGS_LOC}", all_tags_input)
				.replace("${hiveconf:CL_AGG_LOC}", clickstream_input )
				.replace("${hiveconf:OUT_LOC}", output_loaction)
				.replace("${hiveconf:hive.db.name}", hive_db_name)
		        .replace("${hiveconf:VISIT_JOURNEY_LOC}", visit_output);
		

        String finalQueryScript = params + replacedFileContent;
        
        
    

        // write final query to disk
        if (tempLocation != null) {
            String filePath = tempLocation + scriptFile.substring(scriptFile.lastIndexOf(File.separatorChar));
            logger.info("Writing final script to: " + filePath);
            writeFinalScript(filePath, finalQueryScript);
        }
        
        try {
            Class.forName(driverName);
          } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(1);
          }
        logger.info("Starting create statement");
        Connection con = DriverManager.getConnection("jdbc:hive://localhost:10000/default", "", "");
        //jdbc:hive://192.168.1.1:10000
        Statement stmt = con.createStatement();
        logger.info("endingh  create statement");
       
        stmt.executeQuery(finalQueryScript);

   // now execute all Hive queries available in the final script
   //     HiveScriptExecutor hiveExecutor = new HiveScriptExecutor();
   //     hiveExecutor.setMaxRetries(maxRetries);
   //   hiveExecutor.executeScript(finalQueryScript);

        long endTime = System.currentTimeMillis();
        logger.info("ProductOwnerDashboardDynamicQueryTasklet [" + scriptFile + " - " + stepName + "] completed in  " + (endTime - startTime)
                / 1000 + " seconds.");
        return RepeatStatus.FINISHED;
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
                // ignore comments
                if (line.startsWith(COMMENT)) {
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
            // play safe, create directory structure if missing
            file.getParentFile().mkdirs();

            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            br.write(content);
            br.flush();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
    
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    public void setTempLocation(String tempLocation) {
        this.tempLocation = tempLocation;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    

	
	 public String getClickstream_input() {
			return clickstream_input;
		}

		public void setClickstream_input(String clickstream_input) {
			this.clickstream_input = clickstream_input;
		}

		public String getAll_tags_input() {
			return all_tags_input;
		}

		public void setAll_tags_input(String all_tags_input) {
			this.all_tags_input = all_tags_input;
		}

		public String getOutput_loaction() {
			return output_loaction;
		}

		public void setOutput_loaction(String output_loaction) {
			this.output_loaction = output_loaction;
		}
		public String getHive_db_name() {
			return hive_db_name;
		}

		public void setHive_db_name(String hive_db_name) {
			this.hive_db_name = hive_db_name;
		}
		

		public String getVisit_output() {
			return visit_output;
		}

		public void setVisit_output(String visit_output) {
			this.visit_output = visit_output;
		}


}
