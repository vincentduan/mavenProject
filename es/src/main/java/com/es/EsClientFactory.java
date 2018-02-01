package com.es;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

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
                ((TransportClient) client)
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.parseInt(esConf.getPort())));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        System.out.println("esClient:"+client);
        return client;
    }

}
