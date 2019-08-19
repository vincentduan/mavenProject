package cn.ac.iie.controller;

import cn.ac.iie.config.VisiableThreadPoolTaskExecutor;
import cn.ac.iie.service.UserThreadService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/serviceTest")
public class TestController {

    @Autowired
    UserThreadService userThreadService;
    @Autowired
    private VisiableThreadPoolTaskExecutor visiableThreadPoolTaskExecutor;

    @GetMapping("test/{username}")
    public Object test(@PathVariable("username") String username) {
        userThreadService.serviceTest(username);
        JSONObject jsonObject = new JSONObject();
        ThreadPoolExecutor threadPoolExecutor = visiableThreadPoolTaskExecutor.getThreadPoolExecutor();
        jsonObject.put("ThreadNamePrefix", visiableThreadPoolTaskExecutor.getThreadNamePrefix());
        jsonObject.put("TaskCount", threadPoolExecutor.getTaskCount());
        jsonObject.put("completedTaskCount", threadPoolExecutor.getCompletedTaskCount());
        jsonObject.put("activeCount", threadPoolExecutor.getActiveCount());
        jsonObject.put("queueSize", threadPoolExecutor.getQueue().size());
        return jsonObject;
    }

}
