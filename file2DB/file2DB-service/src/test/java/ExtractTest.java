import org.ExtractUtils.ExtractFromSentence;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.dao.ExtractDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.service.ExtractService;
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
    @Autowired
    ExtractService extractService;

    @Test
    public void testExtract(){
        File file = new File("D:/test/ddy.txt");
        //File file = new File("/Users/duandingyang/test/ddy.txt");
        /*try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                ExtractFromSentence.sentence = lineTxt;
                String[] cf1c18 = ExtractFromSentence.tblcf1c18();
                String[] cf1c19 = ExtractFromSentence.tblcf1c19();
                String[] cf1c28 = ExtractFromSentence.tblcf1c28();
                String[] cf1c32 = ExtractFromSentence.tblcf1c32();
                String[] cf1c39 = ExtractFromSentence.tblcf1c39();
                String[] cf1c50 = ExtractFromSentence.tblcf1c50();
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
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Test
    public void producerTestSimple(){
        /*String lineTxt = "{ \"index\" : { \"_index\" : \"test\", \"_type\" : \"t3\", \"_id\" : \"10000000\" } }\n" +
                "{\"cf1\":\"0000008207\", \"cf2\":\"20170808023304\", \"cf3\":{\"c42\":{\"c4\":\"1100008207\"}, \"d1\":{\"c4\":\"1000004751\"}, \"c43\":\"chat\", \"d2\":\"c++\"}, \"cf4\":{\"c18\":\"466187329633641\", \"c19\":\"231899539124898\", \"c32\":\"92541820110\", \"c28\":\"202118428730\", \"c20\":\"1.5.2.8\", \"c50\":\"1100008207\"}, \"cf5\":\"5\", \"cf6\":\"t3\", \"cf7\":\"20170808\", \"cf8\":\"reg1\"}";
        kafkaTemplate.send("test1",lineTxt);*/
    }

    @Test
    public void testExtract2(){
        /*String[] str = new String[2];
        str[0]="0022";
        str[1]="0033";
        extractDao.updateMysqlPerCf1C18ForOne(str);*/
    }

}
