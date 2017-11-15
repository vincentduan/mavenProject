import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/14
 * Time: 12:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/*.xml")
public class MyTest2 {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Test
    public void testTemplateSend() {
        kafkaTemplate.send("test1", "www.d.com");
    }
}
