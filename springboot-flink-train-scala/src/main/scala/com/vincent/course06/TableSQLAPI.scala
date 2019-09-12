package com.vincent.course06

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.scala.BatchTableEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.types.Row


object TableSQLAPI {

  def main(args: Array[String]): Unit = {
    val bEnv = ExecutionEnvironment.getExecutionEnvironment
    val bTableEnv = BatchTableEnvironment.create(bEnv)
    val filePath = "E:/test/sales.csv"
    // 已经拿到DataSet
    val csv = bEnv.readCsvFile[SalesLog](filePath, ignoreFirstLine = true)
    // DataSet => Table
    val salesTable = bTableEnv.fromDataSet(csv)
    // 注册成Table  Table => table
    bTableEnv.registerTable("sales", salesTable)
    // sql
    val resultTable = bTableEnv.sqlQuery("select customerId, sum(amountPaid) money from sales group by customerId")
    bTableEnv.toDataSet[Row](resultTable).print()
  }

  case class SalesLog(transactionId: String, customerId: String, itemId: String, amountPaid: Double
                     )

}
