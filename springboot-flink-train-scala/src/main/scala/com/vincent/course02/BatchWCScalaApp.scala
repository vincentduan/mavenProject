package com.vincent.course02

import org.apache.flink.api.scala.ExecutionEnvironment


/**
  * 使用Scala开发Flink的批处理应用程序
  */
object BatchWCScalaApp {
  def main(args: Array[String]): Unit = {
    val input = "E:/test/input/test.txt"
    val env = ExecutionEnvironment.getExecutionEnvironment
    val text = env.readTextFile(input)
    // 引入隐式转换
    import org.apache.flink.api.scala._

    text.flatMap(_.toLowerCase.split("\t"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .groupBy(0)
      .sum(1)
      .print()
  }
}
