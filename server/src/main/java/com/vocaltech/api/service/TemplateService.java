package com.vocaltech.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private final S3Service s3Service;

    @Autowired
    public TemplateService(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Cacheable(value = "templates", key = "#templateKey")
    public String getTemplate(String templateKey) {
        return s3Service.downloadTemplateAsString(templateKey);
    }
}
