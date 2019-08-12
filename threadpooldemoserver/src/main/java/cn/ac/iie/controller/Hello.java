package cn.ac.iie.controller;

import cn.ac.iie.config.VisiableThreadPoolTaskExecutor;
import cn.ac.iie.service.AsyncService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;


@RestController
@Slf4j
public class Hello {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private VisiableThreadPoolTaskExecutor visiableThreadPoolTaskExecutor;

    @RequestMapping("/")
    public Object submit() {
        log.info("start submit");
        //调用service层的任务
        asyncService.executeAsync();
        log.info("end submit");
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
