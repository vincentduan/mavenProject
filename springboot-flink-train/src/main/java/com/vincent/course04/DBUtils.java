package com.vincent.course04;

import java.util.Random;

public class DBUtils {
    public static String getConnection() {
        return new Random().nextInt(10) + "";
    }
    public static void returnConnection(String connection) {

    }
}
