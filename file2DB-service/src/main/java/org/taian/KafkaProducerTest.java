package org.taian;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-producer.xml")
public class KafkaProducerTest {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Test
    public void testTemplateSend() {
        kafkaTemplate.send("test1", "www.c.com");
    }
}
