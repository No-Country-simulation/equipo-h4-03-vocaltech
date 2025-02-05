package com.vocaltech.api.service.interfaces;

import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.List;

public interface IEmailService {

    @Async
    void sendEmail(String toUser, String subject, String message);

    @Async
    void sendEmailWithFiles(String toUser, String subject, String message, List<File> files);

}
