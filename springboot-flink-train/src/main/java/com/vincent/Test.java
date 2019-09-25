package com.vincent;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;

public class Test {
    public static void main(String[] args) {
        try {
            // 这里用的binance的socket接口，国内调用需要VPN，使用换成你的就行
//            String url = "wss://stream.binance.com:9443/ws/ethbtc@ticker";
//            String url = "wss://stream.binance.com:9443/ws/ethbtc@depth20";
            String url = "ws://192.168.170.134:3000";
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    System.out.println(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {

                }

                @Override
                public void onError(Exception e) {

                }

                @Override
                public void send(String text) throws NotYetConnectedException {
                    System.out.println("senddddddd");
                    super.send(text);
                }
            };
            mWs.connect();
            System.out.println("haha");
            while ((mWs.getReadyState().toString()).equals("NOT_YET_CONNECTED")) {
                System.out.println("connecting");
                Thread.sleep(1000);
            }
            mWs.send("abc");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
