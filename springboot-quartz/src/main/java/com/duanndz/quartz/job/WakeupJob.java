package com.duanndz.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;

/**
 * duan.nguyen
 * Datetime 5/12/20 11:40
 */
@Slf4j
public class WakeupJob implements Job {

    @Value("${wakeup.time}")
    private String wakeupTime;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // repeat job
        log.info("Wakeup Job is executing... {}", wakeupTime);
    }

}
