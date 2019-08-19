package cn.ac.iie.service.impl;

import cn.ac.iie.dao.UserThreadDao;
import cn.ac.iie.service.UserThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserThreadServiceImpl implements UserThreadService {

    @Autowired
    UserThreadDao userThreadDao;

    @Override
    @Async("asyncServiceExecutor")
    public void serviceTest(String username) {
        log.info("开启执行一个Service, 这个Service执行时间为30s, threadId:{}",Thread.currentThread().getId());
        userThreadDao.add(username, Integer.parseInt(Thread.currentThread().getId() +""), "started");
        /*try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        log.info("执行完成一个Service, threadId:{}",Thread.currentThread().getId());
        userThreadDao.update(username, Integer.parseInt(Thread.currentThread().getId() +""), "ended");
    }

    @Override
    public void update(String username, int threadId, String status) {
        userThreadDao.update(username, threadId, status);
    }
}
