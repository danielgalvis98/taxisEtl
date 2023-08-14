name := "taxis-etl"
version := "0.1.0"
scalaVersion := "2.12.17"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.4.1"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.20.0"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.4.1"
libraryDependencies += "software.amazon.awssdk" % "s3" % "2.20.123"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
