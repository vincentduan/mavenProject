package org.taian;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.ExtractUtils.ExtractFromSentence;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.joda.time.DateTime;
import org.service.ExtractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerAwareMessageListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumerListener7 implements ConsumerAwareMessageListener<Integer, String> {

    @Autowired
    ExtractService extractService;

    @Autowired
    ComboPooledDataSource dataSource;

    List<String[]> cf1c18_list = new ArrayList<>();
    List<String[]> cf1c19_list = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    ArrayBlockingQueue<String[]> arrayBlockingQueue_cf1c18 = new ArrayBlockingQueue<>(10);
    ArrayBlockingQueue<String[]> arrayBlockingQueue_cf1c19 = new ArrayBlockingQueue<>(10);

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener7.class);

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record, Consumer<?, ?> consumer) {
        if((record.value().toString()).equals("start")){
            System.out.println("start: " + new DateTime(System.currentTimeMillis()));
        }
        if ((record.value().toString()).equals("end")) {
            System.out.println("end: " + new DateTime(System.currentTimeMillis()));
        }

        //System.out.println("consumer id: "+consumer);
        ExtractFromSentence extractFromSentence = new ExtractFromSentence(record.value());
        String[] cf1c18 = extractFromSentence.tblcf1c18();
        String[] cf1c19 = extractFromSentence.tblcf1c19();
        String[] cf1c28 = extractFromSentence.tblcf1c28();
        String[] cf1c32 = extractFromSentence.tblcf1c32();
        String[] cf1c39 = extractFromSentence.tblcf1c39();
        String[] cf1c50 = extractFromSentence.tblcf1c50();

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
        if (cf1c18[0] != null) {
            String c18 = extractService.tblcf1c18ForOne(cf1c18);
        }
        if (cf1c19[0] != null) {
            String c19 = extractService.tblcf1c19ForOne(cf1c19);
        }
        if (cf1c28[0] != null) {
            String c28 = extractService.tblcf1c28ForOne(cf1c28);
        }
        if (cf1c32[0] != null) {
            String c32 = extractService.tblcf1c32ForOne(cf1c32);
        }
        if (cf1c39[0] != null) {
            String c39 = extractService.tblcf1c39ForOne(cf1c39);
            try {
                System.out.println("总连接数:" + dataSource.getNumConnections());
                System.out.println("最大连接池数:" + dataSource.getMaxPoolSize());
                System.out.println("正在使用连接数:"+dataSource.getNumBusyConnections());
                System.out.println("空闲连接数:"+dataSource.getNumIdleConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (cf1c50[0] != null) {
            String c50 = extractService.tblcf1c50ForOne(cf1c50);
        }

    }


}