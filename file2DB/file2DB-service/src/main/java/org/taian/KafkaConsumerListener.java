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
        String[] cf1c28 = ExtractFromSentence.tblcf1c28();
        String[] cf1c32 = ExtractFromSentence.tblcf1c32();
        String[] cf1c39 = ExtractFromSentence.tblcf1c39();
        String[] cf1c50 = ExtractFromSentence.tblcf1c50();

        /*try {
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
        }*/
        if(cf1c18[0] != null){
            String c18 = extractService.tblcf1c18ForOne(cf1c18);
        }
        if (cf1c19[0] != null){
            String c19 = extractService.tblcf1c19ForOne(cf1c19);
        }
        if(cf1c28[0] != null){
            String c28 = extractService.tblcf1c28ForOne(cf1c28);
        }
        if(cf1c32[0] != null){
            String c32 = extractService.tblcf1c32ForOne(cf1c32);
        }
        if(cf1c39[0] != null){
            String c39 = extractService.tblcf1c39ForOne(cf1c39);
        }
        if(cf1c50[0] != null){
            String c50 = extractService.tblcf1c50ForOne(cf1c50);
        }


    }



}