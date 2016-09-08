drop table if exists all_tags;
CREATE EXTERNAL TABLE all_tags
(
product_Segment STRING,
subsegment STRING,
Journey STRING,
Tags STRING,
Intlink STRING,
Nature STRING,
Journey_start FLOAT,
Journey_End FLOAT,
Start_Tag STRING,
End_Tag STRING)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS textfile
LOCATION '${hiveconf:ALL_TAGS_LOC}'
TBLPROPERTIES('serialization.null.format'='');

