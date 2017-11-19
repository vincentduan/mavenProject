package org.quartz.service;


import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.dao.ScheduleJobDao;
import org.quartz.domain.ScheduleJobEntity;
import org.quartz.qvo.ScheduleJobQuery;
import org.quartz.util.ScheduleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
	
	@Autowired
	private Scheduler scheduler;

	@Autowired
	private ScheduleJobDao schedulerJobDao;
	
	/** 项目启动时，初始化定时器 
	 * @throws Exception */
	@PostConstruct
	public void init() throws Exception {
		List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new ScheduleJobQuery());
		for (ScheduleJobEntity scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJob_Id());
			// 如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			}
			else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

    @Override
    public ScheduleJobEntity queryObject(Long jobId) {
        return schedulerJobDao.queryObject(jobId);
    }

    @Override
    public void run(Long... jobIds) {
        //ScheduleUtils.run(scheduler, queryObject(jobId));
    }

    @Override
    public void save(ScheduleJobEntity scheduleJob) {
        schedulerJobDao.save(scheduleJob);
    }
}
