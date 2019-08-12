package cn.ac.iie.service.impl;

import cn.ac.iie.dao.OperatelogDao;
import cn.ac.iie.service.OperatelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vincent
 * @time 2019-08-03 15:53
 */
@Service
public class OperatelogServiceImpl implements OperatelogService {

    @Autowired
    OperatelogDao operatelogDao;

    public void insert(int id) {
        operatelogDao.addOperatelog(id);
    }

    @Override
    public void addBatch() {
        operatelogDao.addBatch();
    }

    @Override
    public List<String> queryOperateDesc() {
        return operatelogDao.queryDirection();
    }

    @Override
    public List<String> getUserIdByDirection(String direction) {
        return operatelogDao.getUserIdByDirection(direction);
    }


}
