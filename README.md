# ETL project

This project has been designed as starting point to understand and learn  about ETL process.
ETL stands for Extract, Transform and Load, and the main idea of this project is to use open data available about the
New York cities taxis to create an ETL, so you will extract the data from "parquet" files, load the data in memory and
transform the read data into a new one.

## Data

Data dictionaries and datasets can be found in the NYC page: [NYC Portal](https://www.nyc.gov/site/tlc/about/tlc-trip-record-data.page)

## Scenarios

Considered scenarios in the project:

### Case 1: Travel time according location

Using the columns:

* `tpep_pickup_datetime`
* `tpep_dropoff_datetime`
* `Trip_distance`
* `PULocationID`
* `DOLocationID`

### Case 2: Payment by distance

* Using the columns:
* `PULocationID`
* `DOLocationID`
* `Trip_distance`
* `Tolls_amount`
* `Total_amount`

### Case 3: Payment by time

* Using the columns:
* `tpep_pickup_datetime`
* `tpep_dropoff_datetime`
* `Passenger_count`
* `Payment_type`
* `Total_amount`

## Install

To be able to run this project you should have a properly configured environment. The instructions below contains the required
steps you should follow to be able to run the entire project.

### Install java (Linux, Mac, WSL)

Install [sdkman](https://sdkman.io/) and activate the environment

```bash
curl -s "https://get.sdkman.io" | bash
# Inside the project folder install java, spark and hadoop (required once)
sdk env install
# Then you can simply enable the environment with
sdk env
```

### Install python dependencies

We recommend to use a virtual env with python

```bash
python -m venv venv
```

And then install the dependencies, -e in the custom folder reads the pyproject.toml

```bash
pip install -e .
```

## Running the project

Setup the following environment variables

TAXIS_ETL_FILES, TAXIS_ETL_DOWNLOAD_FOLDER, TAXIS_ETL_BASE_URL, TAXIS_ETL_DOWNLOAD_FILES

Files from S3

```bash
export TAXIS_ETL_FILES=yellow_tripdata_2020-01.parquet,yellow_tripdata_2021-01.parquet,yellow_tripdata_2022-01.parquet
export TAXIS_ETL_DOWNLOAD_FOLDER=downloads
export TAXIS_ETL_DOWNLOAD_SOURCE=s3
export TAXIS_ETL_BUCKET_NAME=s3-bucket
```

Files from cloud server

```bash
export TAXIS_ETL_FILES=yellow_tripdata_2020-01.parquet,yellow_tripdata_2021-01.parquet,yellow_tripdata_2022-01.parquet
export TAXIS_ETL_DOWNLOAD_FOLDER=downloads
export TAXIS_ETL_BASE_URL=https://...
export TAXIS_ETL_DOWNLOAD_SOURCE=url
```

For the first time execution download the files by changing the environment variable to true
```bash
export TAXIS_ETL_DOWNLOAD_SOURCE=local
```

Scala is used to run the compiled Java file that contains all the instructions. In this example we are going to run specific classes defined in the code

* Compile the project using Gradle.
  * If you are in IntelliJ, go to the right side on your window and select the Gradle tab
  * Double-click on "`clean`", then "`assemble`" to compile the project
* Create the downloads directory
  * run the command `mkdir downloads`
* Run the following command to execute the class:
  * `spark-submit --class com.taxis.etl.travel_time.TravelTime --master "local[8]" build/libs/taxisEtl.jar 100`
  * `spark-submit --class com.taxis.etl.payments_distance.PaymentsDistance --master "local[8]" build/libs/taxisEtl.jar 100`
  * Wait until the execution finishes and verify the results are in the folder `cd results`

### By

* Santiago Ruiz
* Diego Peña

## Workarounds

### Java exception Class org.apache.hadoop.fs.s3a.S3AFileSystem not found

If you see this exception in the logs when you run the spark submit command

```bash
java.lang.RuntimeException: java.lang.ClassNotFoundException: Class org.apache.hadoop.fs.s3a.S3AFileSystem not found
```

You can add all the dependencies required by hadoop s3 to the spark config file  `$SPARK_HOME/conf/spark-defaults.conf`

Please ensure that environment varibles `$SPARK_HOME` and `$HADOOP_HOME` are setup

> when you run `sdk env` both environment variables are setup, check that running  `echo $SPARK_HOME` and `echo $HADOOP_HOME`

If you use mac/linux/wsl, you run the following command

```bash
echo "spark.driver.extraClassPath $HADOOP_HOME/share/hadoop/tools/lib/*:$HADOOP_HOME/share/hadoop/common/*:$HADOOP_HOME/share/hadoop/hdfs/*:$HADOOP_HOME/share/hadoop/common/lib/woodstox-core-5.4.0.jar:$HADOOP_HOME/share/hadoop/common/lib/stax2-api-4.2.1.jar:$HADOOP_HOME/share/hadoop/common/lib/commons-configuration2-2.8.0.jar" >> $SPARK_HOME/conf/spark-defaults.conf
```
