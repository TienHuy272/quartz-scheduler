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

    private final String GROUP_NAME = "simple-group";
    private final String CLASS_NAME = "simple-class";


    public void scheduleJob(String jobName, String cronExpression) throws SchedulerException {
        try {

            JobDetail jobDetail = jobConfiguration.createJob(
                    (Class<? extends QuartzJobBean>) Class.forName(ExecutionJob.class.getName()), false, context,
                    jobName, GROUP_NAME);

            if (scheduler.checkExists(jobDetail.getKey())) {
                log.warn("Job {} already exists", jobName);
                return;
            }
            Trigger trigger = jobConfiguration
                    .createCronTrigger(jobName, new Date(),cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("Job has been scheduled {}", jobName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
