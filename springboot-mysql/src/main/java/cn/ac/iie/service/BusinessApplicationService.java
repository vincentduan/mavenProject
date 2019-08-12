package cn.ac.iie.service;

import java.util.List;

/**
 * @author vincent
 * @time 2019-08-06 17:18
 */
public interface BusinessApplicationService {

    void batch();

    List<String> getUserByDirection(String direction);

    List<String> getUserByOrgName(String orgName);

    List<String> getUserByDutyDesc(String dutyDesc);
}
