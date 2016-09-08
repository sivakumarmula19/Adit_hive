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


