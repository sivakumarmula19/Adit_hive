<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>com.aexp</groupId>
		<artifactId>aexp</artifactId>
		<version>18.0</version>
	</parent>

	<groupId>com.aexp.ims</groupId>
	<artifactId>jaarvis</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<packaging>jar</packaging>
	<name>big-data-digital-customer</name>
	<url>https://aedc.extra.aexp.com/svn/repos/big-data-digital-customer/</url>
	<scm>
		<connection>scm:svn:https://aedc.extra.aexp.com/svn/repos/big-data-digital-customer/trunk/</connection>
	</scm>


	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<spring.version>3.2.4.RELEASE</spring.version>
		<spring.batch.version>2.2.1.RELEASE</spring.batch.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>com.aexp.ims</groupId>
			<artifactId>big-data-utils</artifactId>
			<version>2.11</version>
			<scope>compile</scope>
			<exclusions>
				<exclusion>
					<groupId>org.apache.pig</groupId>
					<artifactId>pig</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.4</version>
		</dependency>

		

		<dependency>
			<groupId>com.aexp.ims.hive</groupId>
			<artifactId>hive-functions</artifactId>
			<version>2.2</version>
			<scope>compile</scope>
			<!-- exclude all hive-functions dependencies -->
			<exclusions>
				<exclusion>
					<groupId>org.apache.hadoop</groupId>
					<artifactId>hadoop-core</artifactId>
				</exclusion>
				<exclusion>
					<artifactId>hsqldb</artifactId>
					<groupId>hsqldb</groupId>
				</exclusion>
				<exclusion>
					<groupId>org.apache.hive</groupId>
					<artifactId>hive-common</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.apache.hive</groupId>
					<artifactId>hive-cli</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.apache.hive</groupId>
					<artifactId>hive-exec</artifactId>
				</exclusion>

				<exclusion>
					<groupId>com.google.guava</groupId>
					<artifactId>guava</artifactId>
				</exclusion>
				
				
				
				


			</exclusions>
		</dependency>

        <dependency>
            <groupId>org.apache.hive</groupId>
            <artifactId>hive-exec</artifactId>
            <version>0.9.0</version>
            <scope>provided</scope>
            <exclusions>
                <exclusion>
                    <groupId>javax.jdo</groupId>
                    <artifactId>jdo2-api</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>18.0</version>
		</dependency>
		


		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.26</version>
		</dependency>
		<dependency>
			<groupId>org.apache.hadoop</groupId>
			<artifactId>hadoop-core</artifactId>
			<version>0.20.2</version>
			<scope>provided</scope>
		</dependency>

		<!-- Spring Framework dependencies -->

		<!-- Spring Framework dependencies -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-tx</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>${spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-beans</artifactId>
			<version>${spring.version}</version>
		</dependency>
		

		<dependency>
			<groupId>org.springframework.batch</groupId>
			<artifactId>spring-batch-core</artifactId>
			<version>${spring.batch.version}</version>
			<exclusions>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring-beans</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring-context</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring-aop</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring-tx</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.springframework</groupId>
					<artifactId>spring-core</artifactId>
				</exclusion>
			</exclusions>
		</dependency>

		<!-- Test dependencies -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.10</version>
			<scope>test</scope>
		</dependency>
		<!-- mrunit Added Start -->
		<dependency>
			<groupId>org.apache.mrunit</groupId>
			<artifactId>mrunit</artifactId>
			<version>1.0.0</version>
			<classifier>hadoop1</classifier>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-all</artifactId>
			<version>1.8.4</version>
		</dependency>

		<dependency>
			<groupId>commons-httpclient</groupId>
			<artifactId>commons-httpclient</artifactId>
			<version>3.1</version>
		</dependency>
		<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.3</version>
		</dependency>
		<dependency>
			<groupId>commons-logging</groupId>
			<artifactId>commons-logging</artifactId>
			<version>1.2</version>
		</dependency>
		<dependency>
			<groupId>commons-codec</groupId>
			<artifactId>commons-codec</artifactId>
			<version>1.9</version>
		</dependency>
		<dependency>
			<groupId>commons-lang</groupId>
			<artifactId>commons-lang</artifactId>
			<version>2.6</version>
		</dependency>

		<dependency>
			<groupId>commons-configuration</groupId>
			<artifactId>commons-configuration</artifactId>
			<version>1.8</version>
		</dependency>

		<dependency>
			<groupId>org.quartz-scheduler</groupId>
			<artifactId>quartz</artifactId>
			<version>2.2.1</version>
		</dependency>

		<dependency>
			<groupId>log4j</groupId>
			<artifactId>log4j</artifactId>
			<version>1.2.16</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>1.7.5</version>
		</dependency>

		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>
		<!-- mrunit Added End -->
	</dependencies>

	<profiles>
		<profile>
			<id>dev</id>
			<activation>
				<!-- seclected by default -->
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<assembly-config>dev</assembly-config>
			</properties>
		</profile>
		<profile>
			<id>qa</id>
			<properties>
				<assembly-config>qa</assembly-config>
			</properties>
		</profile>
		<profile>
			<id>prod</id>
			<properties>
				<assembly-config>prod</assembly-config>
			</properties>
		</profile>
	</profiles>

	<build>
		<plugins>
			<plugin>
				<artifactId>maven-assembly-plugin</artifactId>
				<version>2.3</version>
				<configuration>
					<descriptors>
						<descriptor>src/assembly/assembly.xml</descriptor>
					</descriptors>
				</configuration>
				<executions>
					<execution>
						<id>make-assembly</id> <!-- this is used for inheritance merges -->
						<phase>package</phase> <!-- bind to the packaging phase -->
						<goals>
							<goal>single</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-eclipse-plugin</artifactId>
				<version>2.9</version>
				<configuration>
					<downloadSources>false</downloadSources>
					<downloadJavadocs>false</downloadJavadocs>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>2.3.2</version>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-resources-plugin</artifactId>
				<version>2.4.1</version>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>2.5</version>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>




<assembly
	xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.2 http://maven.apache.org/xsd/assembly-1.1.2.xsd">
	<id>bin</id>
	<!-- Generates a zip package containing the needed files -->
	<formats>
		<format>zip</format>
	</formats>

	<!-- Adds dependencies to zip package under lib directory -->
	<dependencySets>
		<dependencySet>
			<!-- Project artifact is not copied under library directory since it is 
				added to the root directory of the zip package. -->
			<useProjectArtifact>false</useProjectArtifact>
			<outputDirectory>lib</outputDirectory>
			<unpack>false</unpack>
		</dependencySet>
	</dependencySets>

	<fileSets>
	
		<!-- <fileSet>
			<directory>src/main/scripts/config/${assembly-config}</directory>
			<outputDirectory>config</outputDirectory>
		</fileSet> -->
	
		<!-- Adds startup scripts to the root directory of zip package -->
		<fileSet>
			<directory>src/main/scripts/config</directory>
			<outputDirectory>config</outputDirectory>
			<filtered>true</filtered>
			<includes>
				<include>*.*</include>
			</includes>
		</fileSet>
		<fileSet>
			<directory>src/main/scripts/bin</directory>
			<outputDirectory>bin</outputDirectory>
			<includes>
				<include>*.sh</include>
			</includes>
			<fileMode>0755</fileMode>
		</fileSet>
		<fileSet>
			<directory>src/main/scripts/hive</directory>
			<outputDirectory>hive</outputDirectory>
		</fileSet>
		<fileSet>
			<directory>${basedir}/lib</directory>
			<outputDirectory>lib</outputDirectory>
			<includes>
				<include>*.jar</include>
			</includes>
		</fileSet>

		<!-- adds jar package to the root directory of zip package -->
		<fileSet>
			<directory>${project.build.directory}</directory>
			<outputDirectory></outputDirectory>
			<includes>
				<include>*.jar</include>
			</includes>
		</fileSet>
	</fileSets>

</assembly>


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

<?xml version="1.0" encoding="UTF-8"?>
<b:beans xmlns="http://www.springframework.org/schema/batch" xmlns:b="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd
http://www.springframework.org/schema/batch	http://www.springframework.org/schema/batch/spring-batch-2.2.xsd 
http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">

   	<b:import resource="CommonConfig.xml" />
   	
    <job id="JaarvisRunJob" job-repository="jobRepository">
     
      <step id="createBaseTableStep" next="jaarvisStep">
		<tasklet transaction-manager="transactionManager">
			<b:bean parent="defaultHiveTasklet">
				<b:property name="scriptFile" value="/home/training/Desktop/jaarvis2/jaarvis2/target/jaarvis-0.0.1-SNAPSHOT/hive/BaseTable.hql" />
				<b:property name="params">
					<b:set value-type="java.lang.String" merge="true">
					</b:set>
				</b:property>
					<b:property name="clickstream_input" value="${jaarvis.input.CS_enterprise.path}" /> 
							<b:property name="all_tags_input" value="/home/training/Desktop/jaarvis2/jaarvis2/target/jaarvis-0.0.1-SNAPSHOT/config/All_tags.txt" /> 
					<b:property name="output_loaction" value="${jaarvis.output.file.path}" /> 
					<b:property name="visit_output" value="${jaarvis.output.visit_journey.path}" /> 
					<b:property name="hive_db_name" value="${hive.db.name}" /> 

			</b:bean>
		
		</tasklet>
	 </step>
     
     <step id="jaarvisStep" >
		<tasklet transaction-manager="transactionManager">
			<b:bean parent="defaultHiveTasklet">
				<b:property name="scriptFile" value="/home/training/Desktop/jaarvis2/jaarvis2/target/jaarvis-0.0.1-SNAPSHOT/hive/jaarvis_cornerstone_code.hql" />
				<b:property name="params">
					<b:set value-type="java.lang.String" merge="true">
					</b:set>
				</b:property>
					<b:property name="clickstream_input" value="${jaarvis.input.CS_enterprise.path}" /> 
					<b:property name="all_tags_input" value="/home/training/Desktop/jaarvis2/jaarvis2/target/jaarvis-0.0.1-SNAPSHOT/config/All_tags.txt" /> 
					<b:property name="output_loaction" value="${jaarvis.output.file.path}" /> 
					<b:property name="visit_output" value="${jaarvis.output.visit_journey.path}" /> 
					<b:property name="hive_db_name" value="${hive.db.name}" /> 

			</b:bean>
		
		</tasklet>
	 </step>
 
   
    </job>
    
    
    <b:bean name="defaultHiveTasklet" class="com.aexp.ims.jaarvis.extract.JaarvisDynamicQueryTasklet">
		<b:property name="tempLocation" value="${jaarvis.data.path}/processing/hivetmp" />
		<b:property name="maxRetries" value="1" />
		<b:property name="params">
			<b:set value-type="java.lang.String">
			
				<b:value>CREATE DATABASE IF NOT EXISTS Click_Agg;</b:value>
				<b:value>USE Click_Agg;</b:value>
			</b:set>
		</b:property>
	</b:bean>

</b:beans>

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:p="http://www.springframework.org/schema/p" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd">



	<bean id="transactionManager"
		class="org.springframework.batch.support.transaction.ResourcelessTransactionManager"/>

	<bean id="jobRepository"
		class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean" 
		p:transactionManager-ref ="transactionManager"/>
	

	<bean id="asyncTaskExecutor" class="org.springframework.core.task.SimpleAsyncTaskExecutor" />
	<bean id="syncTaskExecutor" class="org.springframework.core.task.SyncTaskExecutor" />

	<bean id="jobLauncher"
		class="org.springframework.batch.core.launch.support.SimpleJobLauncher">
		<property name="jobRepository" ref="jobRepository" />
	</bean>
	
	

</beans>



#!/bin/bash

current_dir=`dirname "$0"`
JAARVIS_HOME=`cd "$current_dir"; cd ..; pwd`
echo "JAARVIS_HOME $JAARVIS_HOME"

mkdir -v "$JAARVIS_HOME/../logs/" -p
mkdir -v "$JAARVIS_HOME/../output_merged/" -p
mkdir -v "$JAARVIS_HOME/../logs/cronlogs" -p
mkdir -v "$JAARVIS_HOME/../output_zebra/" -p


# add config directory to classpath
CLASSPATH=$JAARVIS_HOME/config

# add jaarvis jar to CLASSPATH
for f in $JAARVIS_HOME/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

# add libs to CLASSPATH
for f in $JAARVIS_HOME/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

# WARNING: HIVE_HOME, this may change with a new release from MapR if directory name changes
HIVE_HOME=/usr/lib/hive

# Add Hive conf directory and jar in classpath
CLASSPATH=${CLASSPATH}:${HIVE_HOME}/conf

for f in ${HIVE_HOME}/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f;
done

# WARNING: HADOOP_HOME, this may change with a new release from MapR if directory name changes
# HADOOP_HOME must be exported else Hive tasks may face errors
export HADOOP_HOME=/usr/lib/hadoop
HADOOP_NATIVE=$HADOOP_HOME/lib/native/Linux-amd64-64

# add amazon-s3 jar and config directory to CLASSPATH
#CLASSPATH=${CLASSPATH}:$HADOOP_HOME/lib/hadoop-0.20.2-dev-core.jar:$HADOOP_HOME/conf:$HADOOP_HOME/lib/amazon-s3.jar
CLASSPATH=${CLASSPATH}:$HADOOP_HOME/lib/hadoop-0.20.2-dev-core.jar:$HADOOP_HOME/conf:$HADOOP_HOME/lib/amazon-s3.jar:$HADOOP_HOME/lib/hadoop-common-2.5.1-mapr-1501.jar:$HADOOP_HOME/lib/hadoop-auth-2.5.1-mapr-1501.jar:$HADOOP_HOME/lib/commons-configuration-1.6.jar:$HADOOP_HOME/lib/zookeeper-3.4.5-mapr-1406.jar 

# add MapRFS jars to CLASSPATH
#for f in ${HADOOP_HOME}/lib/maprfs-*.jar; do
# CLASSPATH=${CLASSPATH}:$f;
#done

# Add run date parameter, so that job can't be restarted again for same day
job=JaarvisRunJob
runDate=`date +%Y%m%d`
#Creating log file
Logfile=$JAARVIS_HOME/../logs/$job\_$runDate.log

echo "Log File: $Logfile"
touch $Logfile


#echo "CLASSPATH: $CLASSPATH"
echo "runDate: $runDate"


#Creating log file
echo "Log File: $JAARVIS_HOME/../logs/$job_$runDate.log "
touch $JAARVIS_HOME/../logs/$job\_$runDate.log
# Spring batch job runner class
job_runner=org.springframework.batch.core.launch.support.CommandLineJobRunner

errorFlag=false


if [ "$errorFlag" = "false" ]
        then
                java -Xmx1024m -Djaarvis.home=$JAARVIS_HOME -Djob=$job -Djava.library.path=$HADOOP_NATIVE -Drun.date=$runDate -classpath "$CLASSPATH" $job_runner Workflow.xml $job run.date=$runDate temp=1
                statuscode=$?
                echo "Status Code from workflow: $statuscode"
                
				
				if [ $statuscode -eq 0 ]
                        then
                                echo "Ingesting file into mainframe"
								rm $JAARVIS_HOME/../output_merged/final_output
								
                fi
				
				
				echo "Status Code from workflow: $statuscode"
fi


