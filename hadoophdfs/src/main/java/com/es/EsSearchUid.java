package com.es;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Map;

public class EsSearchUid extends RichSourceFunction<Tuple2<String, String>> {

    private transient RestHighLevelClient restHighLevelClient;

    @Override
    public void open(Configuration parameters) throws Exception {
        HttpHost httpHost = new HttpHost("swarm-manager", 9200, "http");
        //初始化ElasticSearch-Client
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHost));
    }

    @Override
    public void run(SourceContext<Tuple2<String, String>> ctx) throws Exception {
        SearchRequest searchRequest = new SearchRequest("renyuanku");
        QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("uid", "5445833395"));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(builder);
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchHit[] hits = search.getHits().getHits();

        for(SearchHit hit: hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            ctx.collect(Tuple2.of(sourceAsMap.get("uid").toString(), sourceAsMap.get("imei").toString()));
        }
    }

    @Override
    public void cancel() {

    }
}
