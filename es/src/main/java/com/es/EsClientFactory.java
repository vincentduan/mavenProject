package com.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

//import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * 从工厂类中获取Client方法
 * @author vincent
 *
 */
@Configuration
public class EsClientFactory {

    @Autowired
    private EsConf esConf;
    private String COMMA = ",";
//    private String ERROR_PATH="D:/";


    @Bean(name = "esClient")
    public Client getESClient() {
        Settings settings = Settings.builder()
                .put("cluster.name", esConf.getClusterName()).build();
        Client client = new PreBuiltTransportClient(settings);
        for (String ip : esConf.getIps().split(COMMA)) {
            try {
                // es 5.4.3版本
//                ((TransportClient) client)
//                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(esConf.getPort())));
                // es 6.2.3版本
                ((TransportClient) client)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName(ip), 9301));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        System.out.println("esClient:"+client);
        return client;
    }

}
