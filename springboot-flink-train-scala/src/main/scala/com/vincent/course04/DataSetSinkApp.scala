package com.vincent.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

object DataSetSinkApp {
  def main(args: Array[String]): Unit = {
    val environment = ExecutionEnvironment.getExecutionEnvironment
    val data = 1.to(10)
    val text = environment.fromCollection(data)
    val filePath = "E:/test"
    text.writeAsText(filePath, WriteMode.OVERWRITE).setParallelism(2)
    environment.execute("DataSetSinkApp")
  }
}
