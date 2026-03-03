package com.javatech.config;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;
import java.util.UUID;

public class S3UploadMain {

    public static void main(String[] args) {

        // 🔐 Credentials (learning purpose only)
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(
                        "",
                        ""
                );
        // S3 Client
        S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        String bucketName = "my-first-test-bucket-k1";
        String filePath = "/home/karthik/Downloads/Note"; // 👈 local file
        String key = "uploads/" + UUID.randomUUID() + "-sample.txt";

        // Upload request
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // Upload file
        s3Client.putObject(
                request,
                RequestBody.fromFile(Path.of(filePath))
        );

        System.out.println("✅ File uploaded successfully!");
        System.out.println("S3 Key: " + key);

        s3Client.close();
    }
}
