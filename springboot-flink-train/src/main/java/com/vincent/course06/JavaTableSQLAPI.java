package com.vincent.course06;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

public class JavaTableSQLAPI {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment bEnv = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment bTableEnv = BatchTableEnvironment.create(bEnv);
        DataSource<Sales> salesDataSource = bEnv.readCsvFile("E:/test/sales.csv").ignoreFirstLine().
                pojoType(Sales.class, "transactionId", "customerId", "itemId", "amountPaid");
        Table sales = bTableEnv.fromDataSet(salesDataSource);
        bTableEnv.registerTable("sales", sales);
        Table resultTable = bTableEnv.sqlQuery("select customerId, sum(amountPaid) money from sales group by customerId");
        DataSet<Row> rowDataSet = bTableEnv.toDataSet(resultTable, Row.class);
        rowDataSet.print();
    }

    public static class Sales {
        public String transactionId;
        public String customerId;
        public String itemId;
        public Double amountPaid;

        @Override
        public String toString() {
            return "Sales{" +
                    "transactionId='" + transactionId + '\'' +
                    ", customerId='" + customerId + '\'' +
                    ", itemId='" + itemId + '\'' +
                    ", amountPaid=" + amountPaid +
                    '}';
        }
    }
}
