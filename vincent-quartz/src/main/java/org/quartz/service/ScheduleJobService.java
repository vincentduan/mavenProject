package org.quartz.service;

import org.quartz.domain.ScheduleJobEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ScheduleJobService {

    ScheduleJobEntity queryObject(Long jobId);

	@Transactional
	void run(Long... jobIds);

    void save(ScheduleJobEntity scheduleJob);
}
