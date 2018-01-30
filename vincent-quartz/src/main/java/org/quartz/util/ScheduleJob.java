package org.quartz.util;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.domain.ScheduleJobEntity;


/**
 *
 */
public class ScheduleJob implements Job  {
	
	//private ExecutorService	service	= Executors.newSingleThreadExecutor();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            System.out.println(context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY));
        } catch (Exception e) {
            return;
        } // springboot 下会，莫名报错 ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);
    }
}
