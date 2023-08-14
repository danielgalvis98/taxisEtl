val scala2Version = "2.13.8"
val scala3Version = "3.3.0"
val sparkVersion = "2.4.8"

lazy val root = project
  .in(file("."))
  .settings(
    name := "taxis-etl",
    version := "0.1.0",
    libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.17.2",
    libraryDependencies += "org.apache.spark" % "spark-core_2.11" % sparkVersion,
    libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % sparkVersion,
    libraryDependencies += "software.amazon.awssdk" % "s3" % "2.20.123",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,


    // To make the default compiler and REPL use Dotty
    scalaVersion := scala3Version,

    // To cross compile with Scala 3 and Scala 2
    crossScalaVersions := Seq(scala3Version, scala2Version)
  )
