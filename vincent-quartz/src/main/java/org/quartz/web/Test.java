package org.quartz.web;
import org.quartz.*;
import org.quartz.domain.ScheduleJobEntity;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.util.ScheduleJob;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: vincent
 * Date: 2017/12/6
 * Time: 15:52
 */
public class Test {
    public static void main(String[] args) {
        //通过schedulerFactory获取一个调度器
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = schedulerfactory.getScheduler();


            JobDetail job = JobBuilder.newJob(ScheduleJob.class).withIdentity("job1", "jgroup1").build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("1-59 * * * * ? *"))
                    .startNow().build();
//            JobDataMap dataMap = new JobDataMap();
//            dataMap.put(ScheduleJobEntity.JOB_PARAM_KEY, "ss");
            scheduler.start();
//            scheduler.scheduleJob(job, trigger);
            //scheduler.triggerJob(job.getKey());
//            scheduler.triggerJob(job.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
