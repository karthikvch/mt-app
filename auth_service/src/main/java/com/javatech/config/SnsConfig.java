package com.javatech.config;

import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

//@Configuration
public class SnsConfig {

    @Bean
    public SnsClient snsClient() {
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        "",
                        ""
                );
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build(); // credentials resolved automatically
    }
}
