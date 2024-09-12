package hnt.quartz.scheduler.controller;

import hnt.quartz.scheduler.service.JobSchedulerService;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobSchedulerService jobSchedulerService;

    @PostMapping("/schedule")
    public String scheduleJob(@RequestParam String jobName,
                              @RequestParam String jobGroupName,
                              @RequestParam String cronExpression) {
        try {
            jobSchedulerService.scheduleJob(jobName, jobGroupName, cronExpression);
            return "Job scheduled successfully.";
        } catch (SchedulerException e) {
            return "Error scheduling job: " + e.getMessage();
        }
    }
}
