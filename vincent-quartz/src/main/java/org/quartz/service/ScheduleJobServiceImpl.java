package org.quartz.service;


import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.dao.ScheduleJobDao;
import org.quartz.domain.ScheduleJobEntity;
import org.quartz.qvo.ScheduleJobQuery;
import org.quartz.util.ScheduleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);
	
	/** 项目启动时，初始化定时器 
	 * @throws Exception */
	@PostConstruct
	public void init() throws Exception {
		System.out.println("初始化定时器");
		List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new ScheduleJobQuery());
		for (ScheduleJobEntity scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJob_Id());
			// 如果不存在，则创建
			if (cronTrigger == null) {
                System.out.println("cronTrigger 不存在");
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			}
			else {
                System.out.println("cronTrigger 存在");
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

    @Override
    public ScheduleJobEntity queryObject(Long jobId) {
        return schedulerJobDao.queryObject(jobId);
    }

    @Override
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Override
    public void save(ScheduleJobEntity scheduleJob) {
        schedulerJobDao.save(scheduleJob);
    }
}
