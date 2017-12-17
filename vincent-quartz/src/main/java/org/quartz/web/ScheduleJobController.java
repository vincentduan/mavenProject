package org.quartz.web;

import org.quartz.domain.ScheduleJobEntity;
import org.quartz.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = { "/sys/schedule_do/" })
public class ScheduleJobController {
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	

	
	/** 保存定时任务
	 * 
	 * @throws Exception */
	@ResponseBody
	@RequestMapping(value = { "create" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String save() throws Exception {
		try {
            ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
            scheduleJobEntity.setBean_Name("testTask");
            scheduleJobEntity.setMethod_Name("test");
            scheduleJobEntity.setParams("res");
            scheduleJobEntity.setCron_Expression("0/5 * * * * ?");
            scheduleJobEntity.setStatus(0);
			scheduleJobService.save(scheduleJobEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	@ResponseBody
    @RequestMapping(value = {"query"})
    public String queryQuartzJob(){
        ScheduleJobEntity scheduleJobEntity = scheduleJobService.queryObject(1L);
        System.out.println(scheduleJobEntity);
        return "ok";
    }
	
	/** 立即执行任务 */
	@ResponseBody
	@RequestMapping(value = { "run" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String run(@RequestParam(value = "jobIds")  String jobIds) {
        scheduleJobService.run(Long.parseLong(jobIds));
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "pause" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String pause(@RequestParam(value = "jobIds")  String jobIds) {
		scheduleJobService.pause(Long.parseLong(jobIds));
		return "ok";
	}

	@ResponseBody
	@RequestMapping(value = { "resume" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String resume(@RequestParam(value = "jobIds")  String jobIds) {
		scheduleJobService.resume(Long.parseLong(jobIds));
		return "ok";
	}


	

}
