package org.taian.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        kafkaTemplate.send("test1","haha");
        return "ok";
    }

}
