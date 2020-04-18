package com.vincent.es;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.flink.util.ExceptionUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.common.util.concurrent.EsRejectedExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ElasticSearchSinkUtil {

    static Logger logger = LoggerFactory.getLogger(ElasticSearchSinkUtil.class);

    public static List<HttpHost> getEsAddresses(String hosts) {
        String[] hostList = hosts.split(",");
        List<HttpHost> addresses = new ArrayList<>();
        for (String host : hostList) {
            String[] ip_port = host.split(":");
            String ip = ip_port[0];
            String port = ip_port[1];
            addresses.add(new HttpHost(ip, Integer.parseInt(port)));
        }
        return addresses;
    }

    public static <T> void addSink(List<HttpHost> hosts, int bulkFlushMaxActions, int parallelism,
                                   SingleOutputStreamOperator<T> data, ElasticsearchSinkFunction<T> func) {
        ElasticsearchSink.Builder<T> esSinkBuilder = new ElasticsearchSink.Builder<>(hosts, func);
        esSinkBuilder.setBulkFlushMaxActions(bulkFlushMaxActions);
        esSinkBuilder.setFailureHandler(new ActionRequestFailureHandler() {
            @Override
            public void onFailure(ActionRequest actionRequest, Throwable throwable, int i, RequestIndexer requestIndexer) throws Throwable {
                String description = actionRequest.getDescription();
                System.out.println(description);
                if (ExceptionUtils.findThrowable(throwable, SocketTimeoutException.class).isPresent()) {
                    System.out.println("超时异常");
                } else if (ExceptionUtils.findThrowable(throwable, EsRejectedExecutionException.class).isPresent()) {
                    // 异常1: ES队列满了(Reject异常)，放回队列
                    System.out.println("ES队列满了");
                    requestIndexer.add(actionRequest);
                } else if (ExceptionUtils.findThrowable(throwable, ElasticsearchParseException.class).isPresent()) {
                    System.out.println("parse异常" + description);
                } else if (ExceptionUtils.findThrowable(throwable, ElasticsearchException.class).isPresent()) {
                    System.out.println("出现异常");
                }
            }
        });
        data.addSink(esSinkBuilder.build()).setParallelism(parallelism);
    }
}
