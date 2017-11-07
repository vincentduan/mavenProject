package com.yjl.controller;

import com.yjl.entity.Artical;
import com.yjl.service.ArticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    ArticalService articalService;

    @RequestMapping("/index")
    public String index() {
        System.out.println(articalService.getByExample(1L).get(0).getContent());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/{articalId}/detail",method = RequestMethod.GET)
    public Artical getArtical(@PathVariable("articalId") Long id, Model model){
        Artical artical = articalService.getById(id);
        return artical;
    }

}

