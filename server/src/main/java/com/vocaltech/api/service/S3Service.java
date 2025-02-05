package com.vocaltech.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

    public String downloadTemplateAsString(String key) {
        try {
            byte[] fileBytes = downloadFile(key); // Usa el método existente para descargar el archivo
            return new String(fileBytes, StandardCharsets.UTF_8); // Convierte los bytes a String
        } catch (Exception e) {
            throw new RuntimeException("Error al descargar o convertir el template desde S3", e);
        }
    }

    public List<String> getTemplateKeys() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix("templates/") // Carpeta donde están los templates
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

}

