import os
from dotenv import load_dotenv
from pyspark.sql import SparkSession

load_dotenv()

spark = SparkSession.builder \
        .appName("DemoS3")\
        .getOrCreate()
bucket = os.getenv("TAXIS_ETL_BUCKET_NAME")
filenames = os.getenv("TAXIS_ETL_FILES").split(",")
for key in filenames:
    print("s3a://{}/{}".format(bucket, key))
    df = spark.read.parquet("s3a://{}/{}".format(bucket, key))

spark.stop()