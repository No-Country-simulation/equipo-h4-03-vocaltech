package com.vocaltech.api.service;

import com.vocaltech.api.service.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MailServices implements IEmailService {
    public final SesClient sesClient;
    public final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServices(SesClient sesClient, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.sesClient = sesClient;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Value("${mail.username}")
    private String emailUser;

    @Override
    public void sendEmail(String toUser, String subject, String message) {
        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(toUser).build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).charset("UTF-8").build())
                        .body(Body.builder().text(Content.builder().data(message).charset("UTF-8").build()).build())
                        .build())
                .source(emailUser)
                .build();

        sesClient.sendEmail(emailRequest);
    }

    @Override
    public void sendEmailWithFiles(String toUser, String subject, String message, List<File> files) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(emailUser);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            // Adjuntar múltiples archivos
            if (files != null && !files.isEmpty()) {
                for (File file : files) {
                    if (file != null && file.exists()) {
                        mimeMessageHelper.addAttachment(file.getName(), file);
                    }
                }
            }

            javaMailSender.send(mimeMessage);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
