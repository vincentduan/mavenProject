package com.vincent.course03

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * 使用Scala开发Flink的实时处理应用程序
  */
object StreamingWCScalaApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 引入隐式转换
    import org.apache.flink.api.scala._

    val text = env.socketTextStream("192.168.152.45", 9999)
    text.flatMap(_.split(","))
        .map(x => WC(x,1))
        .keyBy("word")
        .timeWindow(Time.seconds(5))
        .sum("count")
        .print()
        .setParallelism(1)

    env.execute("StreamingWCScalaApp");
  }
  case class WC(word: String, count: Int)
}
