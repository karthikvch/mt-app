package com.javatech.service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

//@Service
public class SqsPollingService {

    private final SqsClient sqsClient;

    public SqsPollingService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void pollMessages(String queueUrl) {

        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(20)   // long polling
                .build();

        ReceiveMessageResponse response =
                sqsClient.receiveMessage(request);

        for (Message message : response.messages()) {

            System.out.println("Message body: " + message.body());

            // delete after successful processing
            deleteMessage(queueUrl, message.receiptHandle());
        }
    }

    private void deleteMessage(String queueUrl, String receiptHandle) {

        DeleteMessageRequest deleteRequest =
                DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(receiptHandle)
                        .build();

        sqsClient.deleteMessage(deleteRequest);
    }
}
