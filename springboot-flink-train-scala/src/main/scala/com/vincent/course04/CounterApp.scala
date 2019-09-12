package com.vincent.course04

import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode

object CounterApp {

  def main(args: Array[String]): Unit = {
    val env  = ExecutionEnvironment.getExecutionEnvironment
    val data = env.fromElements("hadoop","spark","pyspark", "storm")
//    data.map(new RichMapFunction[String, Long] {
//      var counter = 0l
//      override def map(value: String): Long = {
//        counter = counter + 1
//        println("counter:"+counter)
//        counter
//      }
//    }).setParallelism(2).print()
    val info = data.map(new RichMapFunction[String, String] {
      // step1：定义计数器
      val counter  = new LongCounter()
      override def open(parameters: Configuration): Unit = {
        // step2: 注册计数器
        getRuntimeContext.addAccumulator("ele-counts-scala", counter)
      }
      override def map(in: String): String = {
        counter.add(1)
        in
      }
    })
    info.writeAsText("E:/test3", WriteMode.OVERWRITE).setParallelism(4)
    val jobResult=env.execute("CounterApp")
    // step3: 获取计数器
    val num =jobResult.getAccumulatorResult[Long]("ele-counts-scala")
    println("num:" + num )
  }

}
