package com.javatech.service;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.UUID;

//@Service
public class SnsPublisherService {

    private final SnsClient snsClient;

    public SnsPublisherService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void publishMessage(String topicArn, String message) {

        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .messageGroupId("notify_test")
                .messageDeduplicationId(UUID.randomUUID().toString())
                .build();

        PublishResponse response = snsClient.publish(request);

        System.out.println("MessageId = " + response.messageId());
    }
}
