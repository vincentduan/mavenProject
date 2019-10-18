package com.es;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.util.Collector;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EsMapPartition implements MapPartitionFunction<Tuple5<String, String, String, String, String>, Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> {

    HttpHost httpHost = new HttpHost("swarm-manager", 9200, "http");
    private transient RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));

    @Override
    public void mapPartition(Iterable<Tuple5<String, String, String, String, String>> iterable, Collector<Tuple5<String, Set<String>, Set<String>, Set<String>, Set<String>>> collector) throws Exception {
        Iterator<Tuple5<String, String, String, String, String>> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Tuple5<String, String, String, String, String> next = iterator.next();
            String uid = next.f0;
            String imei = next.f1;
            String imsi = next.f2;
            String mac = next.f3;
            String msisdn = next.f4;

            Set<String> imei_set = new HashSet<>();
            Set<String> imsi_set = new HashSet<>();
            Set<String> mac_set = new HashSet<>();
            Set<String> msisdn_set = new HashSet<>();

            imei_set.add(imei);
            imsi_set.add(imsi);
            mac_set.add(mac);
            msisdn_set.add(msisdn);

            SearchRequest searchRequest = new SearchRequest("renyuanku");
            QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("uid", uid));
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(builder);
            searchRequest.source(sourceBuilder);
            SearchResponse search = restHighLevelClient.search(searchRequest);
            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                if (sourceAsMap.containsKey("imei")) {
                    String imei_temp = sourceAsMap.get("imei").toString();
                    imei_set.add(imei_temp);
                }
            }
            collector.collect(Tuple5.of(uid, imei_set, imsi_set, mac_set, msisdn_set));
        }
    }
}
