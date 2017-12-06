package org.quartz.util;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.domain.ScheduleJobEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 */
public class ScheduleJob implements Job  {
	
	//private ExecutorService	service	= Executors.newSingleThreadExecutor();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*Object o = jobExecutionContext.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) JSONObject.toBean(json, ScheduleJobEntity.class);
        // 获取spring bean

        // 任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            // 执行任务
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBean_Name(), scheduleJob.getMethod_Name(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            // 任务执行总时长
            long times = System.currentTimeMillis() - startTime;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("excute====");
    }
}
