package cn.ac.iie.controller;

import cn.ac.iie.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vincent on 2019-7-13 16:27
 */
@RestController
public class IndexController {

    @Autowired
    IndexService indexService;

    @GetMapping("/test")
    public String test(){
        indexService.test();
        return "ok";
    }

}
