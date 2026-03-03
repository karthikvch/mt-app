package com.javatech.scheduler;

import com.javatech.service.SqsPollingService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
class SchedulerConfig {}

//@Service
public class SqsScheduler {

    private final SqsPollingService pollingService;

    public SqsScheduler(SqsPollingService pollingService) {
        this.pollingService = pollingService;
    }

    @Scheduled(fixedDelay = 5000)
    public void poll() {

        String queueUrl =
                "https://sqs.us-east-1.amazonaws.com/219373853224/test_sqs_notify";

        pollingService.pollMessages(queueUrl);
    }
}
