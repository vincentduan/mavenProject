package cn.ac.iie.service;

import java.util.List;

/**
 * @author vincent
 * @time 2019-08-03 15:53
 */
public interface OperatelogService {
    void insert(int id);

    void addBatch();

    // 得到所有操作日志
    List<String> queryOperateDesc();

    // 根据操作日志方向得到用户ID
    List<String> getUserIdByOperateDesc(String direction);

}
