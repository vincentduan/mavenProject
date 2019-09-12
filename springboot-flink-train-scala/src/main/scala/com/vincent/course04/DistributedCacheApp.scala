package com.vincent.course04

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode


object DistributedCacheApp {
  def main(args: Array[String]): Unit = {
    val environment = ExecutionEnvironment.getExecutionEnvironment

    val filePath = "E:/test/hello.txt"
    // step1: 注册一个本地文件
    environment.registerCachedFile(filePath, "pk-scala-dc")
    val data = environment.fromElements("hadoop", "spark", "flink", "pyspark")
    val info=data.map(new RichMapFunction[String, String] {

      //step2: 在open方法中获取到分布式缓存的内容即可
      override def open(parameters: Configuration): Unit = {
        val dcfile = getRuntimeContext.getDistributedCache.getFile("pk-scala-dc")
        val lines = FileUtils.readLines(dcfile)
        import scala.collection.JavaConverters._
        for(ele <- lines.asScala){
          println(ele)
        }
      }

      override def map(value: String): String = {
        value
      }
    })
    info.writeAsText("E:/test3", WriteMode.OVERWRITE).setParallelism(4)
    environment.execute("DistributedCacheApp")
  }
}
