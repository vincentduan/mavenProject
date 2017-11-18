package org.taian.web;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

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
    public String index(){
        //File file = new File("D:/test/ddy.txt");
        File file = new File("/Users/duandingyang/test/ddy.txt");
        try {
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            while (it.hasNext()) {
                String lineTxt = it.nextLine();
                kafkaTemplate.send("test1",lineTxt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

}