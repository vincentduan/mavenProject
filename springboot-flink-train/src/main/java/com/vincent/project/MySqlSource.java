package com.vincent.project;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class MySqlSource extends RichParallelSourceFunction<HashMap<String, String>> {

    private PreparedStatement ps;
    private Connection connection;


    // 用来建立连接
    @Override
    public void open(Configuration parameters) throws Exception {
        connection = getConnection();
        String sql = "select user_id, domain from user_domain_config";
        ps = this.connection.prepareStatement(sql);
        System.out.println("open");
    }

    @Override
    public void close() throws Exception {
        if (ps != null) {
            ps.close();
        }
        if (connection != null) {
            connection.close();
        }


    }

    private Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://swarm-manager:3306/imooc_flink?useUnicode=true&characterEncoding=UTF-8";
            String username = "root";
            String password = "123456";
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----------mysql get connection has exception , msg = " + e.getMessage());
        }
        return con;
    }

    /**
     * 此处是代码的关键，要从mysql表中，把数据读取出来，转成Map进行数据的封装
     * @param sourceContext
     * @throws Exception
     */
    @Override
    public void run(SourceContext<HashMap<String, String>> sourceContext) throws Exception {
        ResultSet resultSet = ps.executeQuery();
        HashMap<String, String> map = new HashMap<>();
        while (resultSet.next()) {
            String user_id = resultSet.getString("user_id");
            String domain = resultSet.getString("domain");
            map.put(domain, user_id);
        }
        sourceContext.collect(map);
    }

    @Override
    public void cancel() {

    }
}
