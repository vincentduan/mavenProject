package org.quartz.dao;


import org.apache.ibatis.annotations.Param;
import org.quartz.domain.ScheduleJobEntity;
import org.quartz.qvo.ScheduleJobQuery;

import java.util.List;

public interface ScheduleJobDao {

    ScheduleJobEntity queryObject(Long jobId);

    void save(ScheduleJobEntity scheduleJob);

    List<ScheduleJobEntity> queryList(@Param("scheduleJobQuery") ScheduleJobQuery scheduleJobQuery) throws Exception;

}
