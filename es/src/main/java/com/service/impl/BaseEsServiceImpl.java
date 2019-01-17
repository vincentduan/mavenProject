package com.service.impl;

import com.service.BaseEsService;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class BaseEsServiceImpl implements BaseEsService {

    @Autowired
    @Qualifier("esClient")
    Client client;

    @Autowired
    @Qualifier("bulkProcessor")
    private BulkProcessor bulkProcessor;

    @Override
    public void testBulk() {
        try {
            System.out.println("bulkprocessor:"+bulkProcessor);
            for(int i = 0; i< 100; i++){
//                bulkProcessor.add(new IndexRequest("twitter", "tweet").source(jsonBuilder().startObject().field("name","vin").field("age", "18").endObject()));
                IndexRequest indexRequest = new IndexRequest("twitter", "tweet");
                indexRequest.source("{\"name\":\"sdfsdf\"}");
                bulkProcessor.add(indexRequest);
        }
            bulkProcessor.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByQuery() {
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(QueryBuilders.rangeQuery("id").gte(0)).source("index_name").get();
        long deleted = bulkByScrollResponse.getDeleted();
        System.out.println(deleted);
    }
}
