package org.taian.web;

import org.ExtractUtils.ExtractFromSentence;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/21
 * Time: 17:55
 */
public class Test {
    public static void main(String[] args) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.152.45:9092");
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.247.130:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        //String fileName = "/Users/duandingyang/test/ddy.txt";
        String fileName = "D:/test/ddy.txt";
        File file = new File(fileName);
        //File file = new File("/root/tmp_data/fakeData/data20171024/FakeData_t3.txt");
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()));
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
//            for(int i = 0; i<20 ;i++){
//                template.send("topic-"+i, "start");
//            }
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                ExtractFromSentence extractFromSentence = new ExtractFromSentence(lineTxt);
                String cf1 = extractFromSentence.matchCf1(lineTxt);
                if (!"".equals(cf1)) {
                    //logger.info("cf1:"+cf1+","+Integer.parseInt(cf1)%20);
                    template.send("topic-" + Integer.parseInt(cf1)%20, lineTxt);
                    //kafkaTemplate.send("topic-"+(new Random().nextInt(20)), lineTxt);
                }
//                int i = new Random().nextInt(20);
                //System.out.println("topic-"+i+","+lineTxt);
            }

//            while (it.hasNext()) {
//                String lineTxt = it.nextLine();
//                template.send("topic-"+Integer.parseInt(lineTxt)%20, lineTxt);
//            }
            for(int i = 0; i<20 ;i++){
                template.send("topic-"+i, "ThisFileEnd");
            }
            template.flush();
            System.out.println("end: producer" + new DateTime(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
