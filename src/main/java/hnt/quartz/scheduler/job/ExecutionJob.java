package hnt.quartz.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class ExecutionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("HNT schedule job at " + context.getJobDetail().getKey());
    }
}
