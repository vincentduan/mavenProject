import org.apache.kafka.clients.consumer.internals.SubscriptionState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.TimeUnit;


public class MyTest2 {
    @Autowired
    private SubscriptionState.Listener listener;

    @Autowired
    private KafkaTemplate<Integer, String> template;

    @Test
    public void testSimple() throws Exception {
        template.send("annotated1", 0, "foo");
        template.flush();
        //assertTrue(this.listener.latch1.await(10, TimeUnit.SECONDS));
    }
}
