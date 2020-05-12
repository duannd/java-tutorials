package com.duanndz.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * duan.nguyen
 * Datetime 5/12/20 15:34
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean quartzScheduler) throws SchedulerException {
        Scheduler scheduler = quartzScheduler.getScheduler();
        scheduler.start();
        return scheduler;
    }

}
