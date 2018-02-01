package com.es;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.List;

@Configuration
public class EsBulkProcessor {

    @Autowired
    @Qualifier("esClient")
    private Client client;

    @Bean(name = "bulkProcessor")
    public BulkProcessor getBulkProcessor() {
        System.out.println(client);
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {

            }

            /**
             * 如果某条数据没有发送成功，例如乱码错误，可做相应处理
             * @param executionId
             * @param request
             * @param response
             */
            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                System.out.println(executionId);
                Iterator<BulkItemResponse> iterator = response.iterator();
                List<DocWriteRequest> requests = request.requests();
                DocWriteRequest docWriteRequest = requests.get(0);
                System.out.println(docWriteRequest);
                if (response.hasFailures()) {
//                    Iterator<BulkItemResponse> iterator = response.iterator();
//                    List<DocWriteRequest> requests = request.requests();
                    //BufferedWriter bufferedWriter = null;
                    try {
                        //bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ERROR_PATH+file.getName(), true)));
                        while (iterator.hasNext()) {
                            BulkItemResponse next = iterator.next();
                            if (next.isFailed()) {
                                System.out.println("id:" + next.getId() + ",ItemId:" + next.getItemId() + ",Type:" + next.getType() + ",status:" + next.status() + ",response:" + next.getResponse() + ",OpType:" + next.getOpType() + ",Index:" + next.getIndex());
                                BulkItemResponse.Failure failure = next.getFailure();
                                Exception cause = failure.getCause();
                                cause.printStackTrace();
//                                DocWriteRequest docWriteRequest = requests.get(next.getItemId());
//                                System.out.println(docWriteRequest);
//                                String errorData = docWriteRequest.toString();
//                                bufferedWriter.write(errorData + "\n");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    finally {
//                        try {
//                            bufferedWriter.flush();
//                            bufferedWriter.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        }).setBulkActions(5).setConcurrentRequests(0).setFlushInterval(TimeValue.timeValueSeconds(1)).build();
        return bulkProcessor;
    }
}
