package com.vocaltech.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.InputStream;
import java.time.Duration;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;


    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
           }

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public String uploadFile(String folder, String key, InputStream fileInputStream, String contentType) {

            try {
                // Construir el key completo con la carpeta
                String fullKey = folder + key;

                // Subir el archivo al bucket
                s3Client.putObject(
                        PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(fullKey)
                                .contentType(contentType)
                                .build(),
                        RequestBody.fromInputStream(fileInputStream, fileInputStream.available())
                );

                return generatePresignedUrl(fullKey, Duration.ofDays(2)); // URL válida por 2 dias
            } catch (Exception e) {
            throw new RuntimeException("Error al subir el archivo a S3", e);
        }
    }

    public String generatePresignedUrl(String key, Duration duration) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(duration)
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el enlace prefirmado", e);
        }
    }

    public byte[] downloadFile(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            InputStream inputStream = s3Client.getObject(getObjectRequest);
            return inputStream.readAllBytes(); // Convertir InputStream a byte[]
        } catch (Exception e) {
            throw new RuntimeException("Error al descargar el archivo desde S3", e);
        }
    }

}

