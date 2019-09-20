package com.vincent.course08


import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.{BucketingSink, DateTimeBucketer}

object FileSystemSinkApp {
  def main(args: Array[String]): Unit = {
    val environment = StreamExecutionEnvironment.getExecutionEnvironment
    val data = environment.socketTextStream("192.168.227.128",9999)

    data.print().setParallelism(1)

    val filepath = "E:/test/sink"
    val sink = new BucketingSink[String](filepath)
    sink.setBucketer(new DateTimeBucketer("yyyy-MM-dd--HHmm"))
    sink.setWriter(new StringWriter())
    sink.setBatchRolloverInterval(20000)
    data.addSink(sink)
    environment.execute("FileSystemSinkApp")
  }
}
