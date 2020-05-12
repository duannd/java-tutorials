package com.duanndz.quartz.command;

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
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(WakeupJob.class)
                .storeDurably()
                .withIdentity("quartz_wakeup_job")
                .withDescription("Invoke Wakeup Job service...")
                .build();

        long timestamp = System.currentTimeMillis() + 30 * 1000;
        Date startAt = new Date(timestamp);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("quartz_wakeup_job")
                .withDescription("Trigger Wakeup Job")
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withRepeatCount(repeatCount)
                        .withIntervalInSeconds(10))
                .build();
        log.info("Schedule a Job");
        this.scheduler.scheduleJob(jobDetail, trigger);
    }
}
