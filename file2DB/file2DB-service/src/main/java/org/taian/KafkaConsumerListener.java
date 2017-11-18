package org.taian;

import org.ExtractUtils.ExtractFromSentence;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.service.ExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class KafkaConsumerListener implements MessageListener<Integer, String> {

    @Autowired
    ExtractService extractService;

    List<String[]> cf1c18_list = new ArrayList<>();
    List<String[]> cf1c19_list = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    ArrayBlockingQueue<String[]> arrayBlockingQueue_cf1c18 = new ArrayBlockingQueue<>(10);
    ArrayBlockingQueue<String[]> arrayBlockingQueue_cf1c19 = new ArrayBlockingQueue<>(10);

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        ExtractFromSentence.sentence = record.value();
        String[] cf1c18 = ExtractFromSentence.tblcf1c18();
        String[] cf1c19 = ExtractFromSentence.tblcf1c19();
        try {
            if(cf1c18[0] != null){
                arrayBlockingQueue_cf1c18.put(cf1c18);
            }
            if (cf1c19[0] != null){
                arrayBlockingQueue_cf1c19.put(cf1c19);
            }
            if(arrayBlockingQueue_cf1c18.size() == 5){
                arrayBlockingQueue_cf1c18.drainTo(cf1c18_list, 5);
            }
            if(arrayBlockingQueue_cf1c19.size() == 5){
                arrayBlockingQueue_cf1c19.drainTo(cf1c19_list, 5);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(cf1c18_list.size() == 5){
            Future future_cf1c18 = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    extractService.tblcf1c18(cf1c18_list);
                    cf1c18_list.clear();
                }
            });
        }
        if (cf1c19_list.size() == 5){
            Future future_cf1c19 = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    extractService.tblcf1c19(cf1c19_list);
                    cf1c19_list.clear();
                }
            });
        }
    }



}
