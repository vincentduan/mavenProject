package com.es;

import com.alibaba.fastjson.JSONObject;
import com.vincent.ElasticSearchSinkUtil;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.Elasticsearch6ApiCallBridge;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

public class EsUtil implements ElasticsearchSinkFunction<Tuple5<String, String, String, String, String>> {


    @Override
    public void process(Tuple5<String, String, String, String, String> data, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
        IndexRequest indexRequest = null;
        try {
            indexRequest = createIndexRequest(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //requestIndexer.add(indexRequest);
    }

    public IndexRequest createIndexRequest(Tuple5<String, String, String, String, String> data) throws IOException {
        //RestHighLevelClient restHighLevelClient = new RestHighLevelClient();

        JSONObject jsonObject = new JSONObject();
        String uid = data.f0;
        jsonObject.put("uid", uid);
//                jsonObject.put("timestamp", data.f1);
//                jsonObject.put("desc_info", JSONObject.parseObject(data.f2));
//                jsonObject.put("related_identity", JSONObject.parseObject(data.f3));
//                jsonObject.put("record_num", data.f4);
//                jsonObject.put("desc_type", data.f5);
//                jsonObject.put("date", data.f6);
        SearchRequest searchRequest = new SearchRequest("renyuanku");
        /*QueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("uid", uid));
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(builder);
        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchHit[] hits = search.getHits().getHits();
        for(SearchHit hit: hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if(sourceAsMap.containsKey("imei")){
                String imei = sourceAsMap.get("imei").toString();
                System.out.println("imei:" + imei);
            }
        }*/
        return Requests.indexRequest()
                .index("internet_user_daily_hk-20190701-20190731-2")
                .type("type").source(jsonObject.toString(), XContentType.JSON);
    }
}
