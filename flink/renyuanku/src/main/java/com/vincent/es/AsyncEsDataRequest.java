package com.vincent.es;

import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.*;


public class AsyncEsDataRequest extends RichAsyncFunction<Tuple5<String, String, String, String, String>, Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> {

    private transient RestHighLevelClient restHighLevelClient;

    @Override
    public void open(Configuration parameters) throws Exception {
        HttpHost httpHost = new HttpHost("swarm-manager", 9200, "http");
        //初始化ElasticSearch-Client
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));
    }

    @Override
    public void close() throws Exception {
        restHighLevelClient.close();
    }

    @Override
    public void asyncInvoke(Tuple5<String, String, String, String, String> input, ResultFuture<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> resultFuture) throws Exception {
        search(input, resultFuture);
    }

    //异步去读Es表
    private void search(Tuple5<String, String, String, String, String> input, ResultFuture<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> resultFuture) {
        SearchRequest searchRequest = new SearchRequest("renyuanku");
        String uid = input.f0;
        QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("uid", uid));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(builder);
        searchRequest.source(sourceBuilder);


        ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
            String imei = input.f1;
            String imsi = input.f2;
            String mac = input.f3;
            String msisdn = input.f4;
            Set<String> imei_set = null;
            Set<String> imsi_set = null;
            Set<String> mac_set = null;
            Set<String> msisdn_set = null;

            //成功
            @Override
            public void onResponse(SearchResponse searchResponse) {
                imei_set = inputToSet(imei);
                imsi_set = inputToSet(imsi);
                mac_set = inputToSet(mac);
                msisdn_set = inputToSet(msisdn);
                SearchHit[] searchHits = searchResponse.getHits().getHits();
                if (searchHits.length > 0) {
                    JSONObject jsonObject = JSONObject.parseObject(searchHits[0].getSourceAsString());
                    if (jsonObject.containsKey("imei")) {
                        String imei = jsonObject.getString("imei");
                        if (imei.contains(",")) {
                            String temp = imei.replaceAll("\\[", "").replaceAll("\\]", "");
                            List<String> list = Arrays.asList(temp.split(","));
                            for (String string : list) {
                                this.imei_set.add(string);
                            }
                        } else {
                            // 只有一个值
                            this.imei_set.add(imei);
                        }
                    }
                    if (jsonObject.containsKey("imsi")) {
                        String imsi = jsonObject.getString("imsi");
                        if (imsi.contains(",")) {
                            String temp = imsi.replaceAll("\\[", "").replaceAll("\\]", "");
                            List<String> list = Arrays.asList(temp.split(","));
                            for (String string : list) {
                                this.imsi_set.add(string);
                            }
                        } else {
                            // 只有一个值
                            this.imsi_set.add(imsi);
                        }
                    }
                    if (jsonObject.containsKey("mac")) {
                        String mac = jsonObject.getString("mac");
                        if (mac.contains(",")) {
                            String temp = mac.replaceAll("\\[", "").replaceAll("\\]", "");
                            List<String> list = Arrays.asList(temp.split(","));
                            for (String string : list) {
                                this.mac_set.add(string);
                            }
                        } else {
                            // 只有一个值
                            this.mac_set.add(mac);
                        }
                    }
                    if (jsonObject.containsKey("msisdn")) {
                        String msisdn = jsonObject.getString("msisdn");
                        if (msisdn.contains(",")) {
                            String temp = msisdn.replaceAll("\\[", "").replaceAll("\\]", "");
                            List<String> list = Arrays.asList(temp.split(","));
                            for (String string : list) {
                                this.msisdn_set.add(string);
                            }
                        } else {
                            // 只有一个值
                            this.msisdn_set.add(msisdn);
                        }
                    }
                }
                resultFuture.complete(Collections.singleton(Tuple5.of(uid, imei_set, imsi_set, mac_set, msisdn_set)));
            }

            //失败
            @Override
            public void onFailure(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                /*if(!"".equals(imei)){
                    imei_set.add(imei);
                }
                if(!"".equals(imsi)){
                    imsi_set.add(imsi);
                }
                if(!"".equals(mac)){
                    mac_set.add(mac);
                }
                if(!"".equals(msisdn)){
                    msisdn_set.add(msisdn);
                }
                resultFuture.complete(Collections.singleton(Tuple5.of(uid, imei_set, imsi_set, mac_set, mac_set)));*/
            }
        };
        restHighLevelClient.searchAsync(searchRequest, listener);
    }

    // 将input中码址转为Set
    public Set<String> inputToSet(String inputMaZhi) {
        Set<String> mazhi = new HashSet<>();
        if (!"".equals(inputMaZhi)) {
            if (inputMaZhi.contains("<>")) {
                String[] split = inputMaZhi.split("<>");
                mazhi.addAll(Arrays.asList(split));
            } else {
                mazhi.add(inputMaZhi);
            }
        }
        return mazhi;
    }
}



