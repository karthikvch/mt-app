package com.javatech.config;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.FileOutputStream;
import java.io.IOException;

public class S3ReadMain {

    public static void main(String[] args) throws IOException {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        "",
                        ""
                );

        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials)
                )
                .build();

        String bucket = "";
        String key = "";

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> s3Object =
                s3Client.getObject(request);

        try (FileOutputStream fos = new FileOutputStream("downloaded.txt")) {
            fos.write(s3Object.readAllBytes());
        }

        System.out.println("✅ File downloaded successfully");

        s3Client.close();
    }
}
