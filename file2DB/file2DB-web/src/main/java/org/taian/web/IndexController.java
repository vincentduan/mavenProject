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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.taian.KafkaConsumerListener1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/14
 * Time: 13:20
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;
   /* @Autowired
    private DefaultKafkaProducerFactory producerFactory;*/

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener1.class);

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String index(@RequestParam("fileName") String fileName) {
        File file = new File(fileName);
        //File file = new File("/root/tmp_data/fakeData/data20171024/FakeData_t3.txt");
        logger.info("start: producer" + new DateTime(System.currentTimeMillis()));
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()));
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
//                kafkaTemplate.send("topic6", lineTxt);
                ExtractFromSentence extractFromSentence = new ExtractFromSentence(lineTxt);
                String cf1 = extractFromSentence.matchCf1(lineTxt);
                if(!"".equals(cf1)){
                    //logger.info("cf1:"+cf1+","+Integer.parseInt(cf1)%20);
                    kafkaTemplate.send("topic-"+Integer.parseInt(cf1)%20, lineTxt);
                    //kafkaTemplate.send("topic-"+(new Random().nextInt(20)), lineTxt);
                }
            }
            for(int i = 0; i<20 ;i++){
                kafkaTemplate.send("topic-"+i, "ThisFileEnd");
            }
            logger.info("end: producer" + new DateTime(System.currentTimeMillis()));
            System.out.println("end: producer" + new DateTime(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public String index2(@RequestParam("fileName") String fileName) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.152.45:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(props);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        File file = new File(fileName);
        //File file = new File("/root/tmp_data/fakeData/data20171024/FakeData_t3.txt");
        logger.info("start: producer" + new DateTime(System.currentTimeMillis()));
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()) );
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                ExtractFromSentence extractFromSentence = new ExtractFromSentence(lineTxt);
                String cf1 = extractFromSentence.matchCf1(lineTxt);
                if(!"".equals(cf1)){
                    template.send("topic-"+Integer.parseInt(cf1)%20, lineTxt);
                }
            }
            logger.info("end: producer" + new DateTime(System.currentTimeMillis()));
            System.out.println("end: producer" + new DateTime(System.currentTimeMillis()) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "test3", method = RequestMethod.GET)
    public String index3() {
        /*KafkaTemplate<Integer, String> template = new KafkaTemplate<>(producerFactory);
        File file = new File(fileName);
        //File file = new File("/root/tmp_data/fakeData/data20171024/FakeData_t3.txt");
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()) + "id:" + record.offset());
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                template.send("topic7", lineTxt);
            }
            System.out.println("end: producer" + new DateTime(System.currentTimeMillis()) + "id:" + record.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return "ok";
    }


}
