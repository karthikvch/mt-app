package com.javatech.config;

import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

//@Configuration
public class SqsConfig {

    @Bean
    public SqsClient sqsClient() {
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        "AKIATGE5TLIUASFVRCXL",
                        "r/dQwwf03yCe6qJRrIF2IeBjhRMOVVhWOg+mR1Gq"
                );
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();
    }
}
