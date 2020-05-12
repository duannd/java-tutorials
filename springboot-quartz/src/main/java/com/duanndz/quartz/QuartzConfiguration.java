package com.duanndz.quartz;

import com.duanndz.quartz.job.WakeupJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * duan.nguyen
 * Datetime 5/12/20 15:34
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

//    private ApplicationContext applicationContext;
//
//    @Autowired
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }

//    @Bean
//    public JobDetailFactoryBean jobDetail() {
//        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
//        jobDetailFactory.setJobClass(WakeupJob.class);
//        jobDetailFactory.setDescription("Invoke Sample Job service...");
//        jobDetailFactory.setDurability(true);
//        return jobDetailFactory;
//    }
//
//    @Bean
//    public SimpleTriggerFactoryBean trigger(JobDetail jobDetail) {
//        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
//        trigger.setJobDetail(jobDetail);
//        trigger.setRepeatInterval(30 * 1000); // repeat each 30s
//        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//        return trigger;
//    }

//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        return jobFactory;
//    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setJobFactory(springBeanJobFactory());
////        factory.setQuartzProperties(quartzProperties());
//        return factory;
//    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean quartzScheduler) throws SchedulerException {
        Scheduler scheduler = quartzScheduler.getScheduler();
        scheduler.start();
        return scheduler;
    }

}
