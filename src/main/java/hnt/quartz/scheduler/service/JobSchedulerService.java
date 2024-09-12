package hnt.quartz.scheduler.service;

import hnt.quartz.scheduler.config.JobConfiguration;
import hnt.quartz.scheduler.job.ExecutionJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Date;

@SuppressWarnings("ALL")
@Service
@Slf4j
public class JobSchedulerService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobConfiguration jobConfiguration;
    @Autowired
    private ApplicationContext context;

    public void scheduleJob(String jobName, String jobGroup, String cronExpression) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
                .withIdentity(jobName, jobGroup)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobName + "Trigger", jobGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        if (scheduler.checkExists(jobDetail.getKey())) {
            throw new SchedulerException("job already exists");
        }

        scheduler.scheduleJob(jobDetail, trigger);
    }

}
