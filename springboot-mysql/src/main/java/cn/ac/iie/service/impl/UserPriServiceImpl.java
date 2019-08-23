package cn.ac.iie.service.impl;

import cn.ac.iie.dao.UserPriDao;
import cn.ac.iie.service.UserPriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPriServiceImpl implements UserPriService {

    @Autowired
    UserPriDao userPriDao;

    @Override
    public List<String> getPriByUserId(String userId) {
        return userPriDao.getPriByUserId(userId);
    }
}
