package com.javatech.controller;

import com.javatech.service.SnsPublisherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@RestController
//@RequestMapping("/api/sns")
public class SnsController {

    private final SnsPublisherService snsPublisherService;

    public SnsController(SnsPublisherService snsPublisherService) {
        this.snsPublisherService = snsPublisherService;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody String message) {

        String topicArn =
                "arn:aws:sns:us-east-1:219373853224:test_sns_notify.fifo";

        snsPublisherService.publishMessage(topicArn, message);

        return "Message published to SNS";
    }
}
