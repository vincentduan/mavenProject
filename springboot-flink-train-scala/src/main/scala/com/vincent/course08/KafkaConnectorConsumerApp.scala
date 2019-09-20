package com.vincent.course08

import java.util.Properties

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.api.scala._

object KafkaConnectorConsumerApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.227.128:9092")
    properties.setProperty("group.id", "test")
    env
      .addSource(new FlinkKafkaConsumer[String]("mytest", new SimpleStringSchema(), properties))
      .print()

    env.execute("KafkaConnectorConsumerApp")
  }

}
