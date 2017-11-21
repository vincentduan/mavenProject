package org.taian.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String index(@RequestParam("fileName") String fileName) {
        File file = new File(fileName);
        //File file = new File("/root/tmp_data/fakeData/data20171024/FakeData_t3.txt");
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()));
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                kafkaTemplate.send("topic7", lineTxt);
            }
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
        System.out.println("start: producer" + new DateTime(System.currentTimeMillis()));
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                template.send("topic7", lineTxt);
            }
            System.out.println("end: producer" + new DateTime(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }


}
