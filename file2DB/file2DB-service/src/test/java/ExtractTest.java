import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.dao.ExtractDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/17
 * Time: 13:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/*.xml")
public class ExtractTest {

    @Autowired
    ExtractDao extractDao;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Test
    public void testExtract(){
        String[] str = new String[2];
        str[0]="0022";
        str[1]="0033";
        String[] str2 = new String[2];
        str2[0]="0044";
        str2[1]="0055";
        String[] str3 = new String[2];
        str3[0]="0044";
        str3[1]="0055";
        List<String[]> list = new ArrayList<>();
        list.add(str);
        list.add(str2);
        list.add(str3);
        extractDao.updateMysqlPerCf1C18(list);
    }

    @Test
    public void producerTest(){
        File file = new File("D:/test/");
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                kafkaTemplate.send("test1",lineTxt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void producerTestSimple(){
        String lineTxt = "{ \"index\" : { \"_index\" : \"test\", \"_type\" : \"t3\", \"_id\" : \"10000000\" } }\n" +
                "{\"cf1\":\"0000008207\", \"cf2\":\"20170808023304\", \"cf3\":{\"c42\":{\"c4\":\"1100008207\"}, \"d1\":{\"c4\":\"1000004751\"}, \"c43\":\"chat\", \"d2\":\"c++\"}, \"cf4\":{\"c18\":\"466187329633641\", \"c19\":\"231899539124898\", \"c32\":\"92541820110\", \"c28\":\"202118428730\", \"c20\":\"1.5.2.8\", \"c50\":\"1100008207\"}, \"cf5\":\"5\", \"cf6\":\"t3\", \"cf7\":\"20170808\", \"cf8\":\"reg1\"}";
        kafkaTemplate.send("test1",lineTxt);
    }

}
