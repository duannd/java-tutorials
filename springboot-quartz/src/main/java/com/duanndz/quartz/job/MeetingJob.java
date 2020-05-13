package com.duanndz.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * duan.nguyen
 * Datetime 5/13/20 10:03
 */
@Slf4j
public class MeetingJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Meeting job executing...");
    }
}
