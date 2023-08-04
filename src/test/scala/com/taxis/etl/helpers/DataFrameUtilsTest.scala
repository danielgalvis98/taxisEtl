package com.taxis.etl.helpers

import com.taxis.etl.utils.Spark
import com.taxis.etl.utils.Spark.spark
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SQLImplicits, SparkSession}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DataFrameUtilsTest extends AnyFunSuite with BeforeAndAfterEach with BeforeAndAfterAll { self =>

  @transient var ss: SparkSession = null
  @transient var sc: SparkContext = null
  @transient var unit: Unit = null

  private object testImplicits extends SQLImplicits {
    protected override def _sqlContext: SQLContext = self.ss.sqlContext
  }
  import testImplicits._

  override def beforeAll(): Unit = {
//    val sparkConfig = new SparkConf()
//    sparkConfig.set("spark.broadcast.compress", "false")
//    sparkConfig.set("spark.shuffle.compress", "false")
//    sparkConfig.set("spark.shuffle.spill.compress", "false")
//    sparkConfig.set("spark.master", "local")

    unit = Spark.init("sparkTest")
  }

  override def afterAll(): Unit = {

  }
  test("filter by column and prefix ends with") {
    val inputDf = spark.createDataFrame(Seq(
      ("0001", "QA HUB"),
      ("0002", "ML GROUP"),
      ("0003", "TEST STRATEGIES GROUP"),
      ("0004", "GROUP OF INVESTIGATION")
    )).toDF("ID", "NAME")

    val expectedResult = spark.createDataFrame(Seq(
      ("0002", "ML GROUP"),
      ("0003", "TEST STRATEGIES GROUP")
    )).toDF("ID", "NAME").collect()

    assertResult(expectedResult)(DataFrameUtils.filterByColumnAndPrefixEndsWith(inputDf, "NAME", "GROUP").get)
    ss.stop()
  }

}
