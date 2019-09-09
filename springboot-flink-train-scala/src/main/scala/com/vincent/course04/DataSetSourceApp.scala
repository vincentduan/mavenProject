package com.vincent.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

object DataSetSourceApp {

  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    //fromCollection(env)
    //    textFile(env)
    //    csvFile(env)
    //    readRecursiveFiles(env)
    readCompressionFiles(env)
  }

  def readCompressionFiles(env: ExecutionEnvironment): Unit = {
    val filePath = "E:/test/my.tar.gz"
    env.readTextFile(filePath).print()
  }

  def readRecursiveFiles(env: ExecutionEnvironment): Unit = {
    val filePath = "E:/test/nested"
    val parameter = new Configuration()
    parameter.setBoolean("recursive.file.enumeration", true)
    env.readTextFile(filePath).withParameters(parameter).print()
  }

  def csvFile(env: ExecutionEnvironment): Unit = {
    import org.apache.flink.api.scala._
    val filePath = "E:/test/input/people.csv"
    //    env.readCsvFile[(String, Int, String)](filePath, ignoreFirstLine = true).print()
    //    env.readCsvFile[(String, Int)](filePath, ignoreFirstLine = true, includedFields = Array(0, 1)).print()
    //    env.readCsvFile[MyCaseClass](filePath, ignoreFirstLine = true, includedFields = Array(0, 1)).print()
    env.readCsvFile[People](filePath, ignoreFirstLine = true, pojoFields = Array("name", "age", "job")).print()
  }

  case class MyCaseClass(name: String, age: Int)

  def textFile(env: ExecutionEnvironment): Unit = {
    //val filePath = "E:/test/input/hello.txt"
    val filePath = "E:/test/input"
    env.readTextFile(filePath).print()
  }


  def fromCollection(env: ExecutionEnvironment): Unit = {
    import org.apache.flink.api.scala._

    val data = 1 to 10
    env.fromCollection(data).print()
  }

}
