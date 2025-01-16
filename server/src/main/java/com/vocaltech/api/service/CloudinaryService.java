package com.vocaltech.api.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${cloudinary.name}") String cloudName,
                             @Value("${cloudinary.api.key}") String apiKey,
                             @Value("${cloudinary.api.secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }


    public String uploadFile(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        return (String) uploadResult.get("url"); // Retorna la URL pública del archivo
    }


    public File downloadFileFromCloudinary(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        Path tempFile = Files.createTempFile("temp-file", ".tmp"); // Crea un archivo temporal
        try (InputStream in = url.openStream()) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile.toFile(); // Retorna el archivo descargado
    }
}

