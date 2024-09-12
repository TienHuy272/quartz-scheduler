package hnt.quartz.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExecutionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        Thread thread = Thread.currentThread();

        log.info("Thread {} : Job name {} in job group {}",
                thread.getName(),
                context.getJobDetail().getKey().getName(),
                context.getJobDetail().getKey().getGroup());
    }
}
