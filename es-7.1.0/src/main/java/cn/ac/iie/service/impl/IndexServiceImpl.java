package cn.ac.iie.service.impl;

import cn.ac.iie.service.IndexService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by vincent on 2019-7-13 16:26
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;

    @Override
    public void test() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0);
        sourceBuilder.size(1);
        TermQueryBuilder termQueryBuilder;
        termQueryBuilder = QueryBuilders.termQuery("info_id", "bbab7655ff916f629a3182d992f850f4");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(termQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("govtrack_us");
        System.out.println(boolBuilder);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(search.getHits().getHits()[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
