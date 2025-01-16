package com.vocaltech.api.service.interfaces;

import org.springframework.scheduling.annotation.Async;

import java.io.File;

public interface IEmailService {

    @Async
    void sendEmail(String toUser, String subject, String message);

    @Async
    void sendEmailWithFile(String toUser, String subject, String message, File file);

}
