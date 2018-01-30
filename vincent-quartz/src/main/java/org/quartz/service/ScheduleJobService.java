package org.quartz.service;

import org.quartz.domain.ScheduleJobEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ScheduleJobService {

    ScheduleJobEntity queryObject(Long jobId);

	void run(Long... jobIds);

	void pause(Long jobId);

    void save(ScheduleJobEntity scheduleJob);

    void resume(long l);
}
