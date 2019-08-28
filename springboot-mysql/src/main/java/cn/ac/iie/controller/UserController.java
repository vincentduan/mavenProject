package cn.ac.iie.controller;

import cn.ac.iie.service.UserPriService;
import cn.ac.iie.utils.ResponseVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserPriService userPriService;

    @GetMapping("/org/{userid}/priIDs")
    public Object getPriByOrg(@PathVariable("userid") String userID){
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("db1", 70);
        map.put("db8", 60);
        map.put("db10", 20);
        map.put("db9", 9);
        map.put("db7", 3);
        return ResponseVOUtil.success(map);
    }

    @GetMapping("/business/{userid}/priIDs")
    public Object getPriByUserBusiness(@PathVariable("userid") String userID){
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("db2", 50);
        map.put("db6", 40);
        map.put("db1", 2);
        map.put("db3", 1);
        return ResponseVOUtil.success(map);
    }

}
