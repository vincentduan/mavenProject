package com.web;

import com.service.BaseEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private BaseEsService baseEsService;

    @ResponseBody
    @RequestMapping(value = "test")
    public String index(){
        baseEsService.testBulk();
        return "ok";
    }

}
