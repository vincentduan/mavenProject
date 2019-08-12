package cn.ac.iie.service.impl;

import cn.ac.iie.dao.BusinessApplicationDao;
import cn.ac.iie.service.BusinessApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vincent
 * @time 2019-08-06 17:19
 */
@Service
public class BusinessApplicationServiceImpl implements BusinessApplicationService {

    @Autowired
    BusinessApplicationDao businessApplicationDao;

    @Override
    public void batch() {
        businessApplicationDao.batch();
    }

    @Override
    public List<String> getUserByDirection(String direction) {
        return businessApplicationDao.getUserByDirection(direction);
    }

    @Override
    public List<String> getUserByOrgName(String orgName) {
        return businessApplicationDao.getUserByOrgName(orgName);
    }

    @Override
    public List<String> getUserByDutyDesc(String dutyDesc) {
        return businessApplicationDao.getUserByDutyDesc(dutyDesc);
    }
}
