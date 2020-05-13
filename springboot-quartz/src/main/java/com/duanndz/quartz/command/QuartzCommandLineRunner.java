package com.duanndz.quartz.command;

import com.duanndz.quartz.job.MeetingJob;
import com.duanndz.quartz.job.WakeupJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * duan.nguyen
 * Datetime 5/12/20 17:26
 */
@Slf4j
@Component
public class QuartzCommandLineRunner implements CommandLineRunner {

    @Value("${interval.count}")
    private int repeatCount;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        if (this.scheduler.checkExists(JobKey.jobKey("quartz_wakeup_job"))) {
            log.info("existed quartz_wakeup_job and delete it.");
            this.scheduler.deleteJob(JobKey.jobKey("quartz_wakeup_job"));
        }
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(WakeupJob.class)
                .storeDurably(true)
                .withIdentity("quartz_wakeup_job")
                .withDescription("Invoke Wakeup Job service...")
                .build();

        long timestamp = System.currentTimeMillis() + 60 * 1000;
        Date startAt = new Date(timestamp);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("quartz_wakeup_job")
                .withDescription("Trigger Wakeup Job")
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(repeatCount)
                        .withIntervalInSeconds(10))
                .build();
        log.info("Schedule a wakeup job");
        this.scheduler.scheduleJob(jobDetail, trigger);

        if (this.scheduler.checkExists(JobKey.jobKey("quartz_meeting_job"))) {
            log.info("existed quartz_meeting_job and delete it.");
            this.scheduler.deleteJob(JobKey.jobKey("quartz_meeting_job"));
        }
        JobDetail meetingJob = JobBuilder
                .newJob(MeetingJob.class)
                .storeDurably(true)
                .withIdentity("quartz_meeting_job")
                .withDescription("Invoke meeting job")
                .build();
        Trigger meetingTrigger = TriggerBuilder.newTrigger()
                .withIdentity("quartz_meeting_job")
                .startAt(new Date(System.currentTimeMillis() + 65 * 1000))
                .build();
        log.info("Schedule a meeting job");
        this.scheduler.scheduleJob(meetingJob, meetingTrigger);
    }
}
