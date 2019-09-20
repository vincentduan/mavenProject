package com.vincent.course08

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper

object KafkaConnectorProducerApp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 从socket接受数据，通过Flink，将数据Sink到kafka
    val data=env.socketTextStream("192.168.227.128", 9999)
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "192.168.227.128:9092")
    properties.setProperty("group.id", "test")
    val kafkaSink = new FlinkKafkaProducer[String]("mytest", new KeyedSerializationSchemaWrapper[String](new SimpleStringSchema()), properties)
    data.addSink(kafkaSink)
    env.execute("KafkaConnectorProducerApp")

  }
}
