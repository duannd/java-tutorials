# Dynamic Job Scheduling with Quartz and Spring

## Concepts

1. Job
    - an interface to be implemented by components that you wish to have executed by the scheduler.
    - The interface has one method execute(...). 
    - This is where your scheduled task runs. 
    - Information on the JobDetail and Trigger is retrieved using the JobExecutionContext.
    ```
        package org.quartz;
        
        public interface Job {
          public void execute(JobExecutionContext context) throws JobExecutionException;
        }
    ```
    
2. JobDetail
    - used to define instances of Jobs.
    - This defines how a job run. 
    - Whatever data you want available to the Job when it is instantiated is provided through the JobDetail.
    ```$xslt
        // define the job and tie it to the Job implementation
        JobDetail job = newJob(EmailJob.class)
          .withIdentity("myJob", "group1") // name "myJob", group "group1"
          .build();
    ```
 
3. Trigger 
    - a component defines the schedule upon which a given Job will be executed.
    - The trigger provides instruction on when the job run.
    - Quartz provides a DSL (TriggerBuilder) for constructing Trigger instances:
    ```$xslt
        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = newTrigger()
          .withIdentity("myTrigger", "group1")
          .startNow()
          .withSchedule(simpleSchedule()
            .withIntervalInSeconds(40)
            .repeatForever())            
          .build();
    ```
    
4. Scheduler
    - the main API for interacting with the scheduler.
    - A Scheduler’s life-cycle is bounded by the creation, via a SchedulerFactory and a call to its shutdown() method.
    - Once created the Scheduler interface can be used to add, remove, and list Jobs and Triggers, 
       and perform other scheduling-related operations (such as pausing a trigger). 
    - However, the Scheduler will not actually act on any triggers (execute jobs) until it has been started with the start() method.
    
## Scope

1. create jobs:
    ```$xslt
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
    ```

2. retrieve existing jobs:
    ```$xslt
        scheduler.getJobDetail(jobKey);
    ```

3. update existing jobs:
   ```$xslt
        // store, and set overwrite flag to 'true'
        scheduler.addJob(jobDetail, true);
    ``` 

4. delete existing jobs:
   ```$xslt
        scheduler.deleteJob(jobKey);
    ```

5. pause jobs:
   ```
        scheduler.pauseJob(jobKey);
   ```

6. resume jobs:
    ```$xslt
        scheduler.resumeJob(jobKey);
    ```

##  About Job stores

-   JobStore’s are responsible for keeping track of all the “work data” that you give to the scheduler: 
    jobs, triggers, calendars, etc. 
-   There are three types of Jobstores that are available in Quartz:
    1. RAMJobStore: 
        is the simplest JobStore to use, it is also the most performant (in terms of CPU time).
        RAMJobStore gets its name in the obvious way: it keeps all of its data in RAM.
        
    2. JDBCJobStore:
        is also aptly named - it keeps all of its data in a database via JDBC.
        Because of this it is a bit more complicated to configure than RAMJobStore, and it also is not as fast.
        










