package com.vincent.course04

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.scala.ExecutionEnvironment

import scala.collection.mutable.ListBuffer

//隐式转化
import org.apache.flink.api.scala._

object DataSetTransformationApp {

  def main(args: Array[String]): Unit = {
    val environment = ExecutionEnvironment.getExecutionEnvironment
    //        mapFunction(environment)
    //    filterFunction(environment)
    //    mapFunction(environment)
    //    mapPartitionFunction(environment)
    //    firstFunction(environment)
    //    flatMapFunction(environment)
    //    distinctFunction(environment)
    //    joinFunction(environment)
//    outJoinFunction(environment)
    crossFunction(environment)
  }

  def crossFunction(env: ExecutionEnvironment): Unit = {
    val info1 = List("乔峰", "慕容复")
    val info2 = List(3,1,0)
    val data1 = env.fromCollection(info1)
    val data2 = env.fromCollection(info2)
    data1.cross(data2).print()
  }

  def outJoinFunction(env: ExecutionEnvironment): Unit = {
    val info1 = ListBuffer[(Int, String)]() //编号 名字
    info1.append((1, "hadoop"))
    info1.append((2, "spark"))
    info1.append((3, "flink"))
    info1.append((4, "java"))

    val info2 = ListBuffer[(Int, String)]() //编号 城市
    info2.append((1, "北京"))
    info2.append((2, "上海"))
    info2.append((3, "深圳"))
    info2.append((5, "广州"))
    val data1 = env.fromCollection(info1)
    val data2 = env.fromCollection(info2)
    data1.leftOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (second == null) {
        (first._1, first._2, "-")
      }else {
        (first._1, first._2, second._2)
      }

    }).print() //左外连接 把左边的所有数据展示出来

    data1.rightOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (first == null) {
        (second._1, "-", second._2)
      }else {
        (first._1, first._2, second._2)
      }

    }).print()

    data1.fullOuterJoin(data2).where(0).equalTo(0).apply((first, second) => {
      if (first == null) {
        (second._1, "-", second._2)
      }else if (second == null){
        (first._1, first._2, "-")
      } else {
        (first._1, first._2, second._2)
      }
    }).print()
  }

  def joinFunction(env: ExecutionEnvironment): Unit = {
    val info1 = ListBuffer[(Int, String)]() //编号 名字
    info1.append((1, "hadoop"))
    info1.append((2, "spark"))
    info1.append((3, "flink"))
    info1.append((4, "java"))

    val info2 = ListBuffer[(Int, String)]() //编号 城市
    info2.append((1, "北京"))
    info2.append((2, "上海"))
    info2.append((3, "深圳"))
    info2.append((5, "广州"))

    val data1 = env.fromCollection(info1)
    val data2 = env.fromCollection(info2)
    data1.join(data2).where(0).equalTo(0).apply((first, second) => {
      (first._1, first._2, second._2)
    }).print()
  }

  def distinctFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[(String)]()
    info.append("hadoop,spark");
    info.append("hadoop,flink");
    info.append("flink,flink");
    val data = env.fromCollection(info)
    data.flatMap(_.split(",")).distinct().print()
  }

  def flatMapFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[(String)]()
    info.append("hadoop,spark");
    info.append("hadoop,flink");
    info.append("flink,flink");
    val data = env.fromCollection(info)
    data.flatMap(_.split(",")).print()
    data.flatMap(_.split(",")).map((_, 1)).groupBy(0).sum(1).print()
  }

  def firstFunction(env: ExecutionEnvironment): Unit = {
    val info = ListBuffer[(Int, String)]()
    info.append((1, "hadoop"))
    info.append((1, "spark"))
    info.append((1, "flink"))
    info.append((2, "java"))
    info.append((2, "springboot"))
    info.append((3, "linux"))
    info.append((4, "vue"))
    val data = env.fromCollection(info)
    data.first(3).print()
    data.groupBy(0).first(2).print()
    data.groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print()
  }

  // DataSource 中有100个元素,把结果存储在数据库中
  def mapPartitionFunction(env: ExecutionEnvironment): Unit = {
    val students = new ListBuffer[String]
    for (i <- 1 to 100) {
      students.append("Student" + i)
    }
    val data = env.fromCollection(students).setParallelism(4)
    /*data.map(x=>{
      // 每一个元素要存储到数据库中去，肯定需要先获取到connection
      val connection = DBUtils.getConnection()
      println(connection + " ... ")
      // TODO .... 保存数据到DB
      DBUtils.returnConnection(connection)
    }).print()*/
    data.mapPartition(x => {
      val connection = DBUtils.getConnection()
      println(connection + " ... ")
      // TODO .... 保存数据到DB
      DBUtils.returnConnection(connection)
      x
    }).print()
  }

  def filterFunction(env: ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    data.map(_ + 1).filter(_ > 5).print()
  }

  def mapFunction(env: ExecutionEnvironment): Unit = {
    val data = env.fromCollection(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    data.print()
    // 对data中的每一个元素都去做一个+1操作
    //data.map((x:Int) => x + 1 ).print()
    //    data.map((x) => x + 1).print()
    //    data.map(x => x + 1).print()
    data.map(_ + 1).print()
  }

}
