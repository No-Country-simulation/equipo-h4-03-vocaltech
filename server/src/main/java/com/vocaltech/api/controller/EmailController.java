//package com.vocaltech.api.controller;
//
//import com.vocaltech.api.dto.request.email.EmailFiletDTO;
//import com.vocaltech.api.dto.request.email.EmailRequestDTO;
//import com.vocaltech.api.service.CloudinaryService;
////import com.vocaltech.api.service.EmailService;
//import com.vocaltech.api.service.MailServices;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.io.File;
//
//
//@RestController
//@RequestMapping("/api/emails")
//@SecurityRequirement(name = "bearer-key")
//public class EmailController {
//
//    private final EmailService emailService;
//    private final MailServices mailService;
//    private final CloudinaryService cloudinaryService;
//
//    @Autowired
//    public EmailController(@Lazy EmailService emailService, MailServices mailServices, @Lazy CloudinaryService cloudinaryService) {
//        this.emailService = emailService;
//        this.mailService = mailServices;
//        this.cloudinaryService = cloudinaryService;
//    }
//
//    @PostMapping("/send")
//    public ResponseEntity<String> sendEmail(@RequestBody @Valid EmailRequestDTO emailRequest) {
//        try {
//            for (String recipient : emailRequest.toUser()) {
//                mailService.sendEmail(recipient, emailRequest.subject(), emailRequest.message());
//            }
//            return ResponseEntity.ok("Emails sent successfully to " + emailRequest.toUser());
//        } catch (Exception ex) {
//            return ResponseEntity.status(500).body("Error sending emails: " + ex.getMessage());
//        }
//    }
//
//
//    @PostMapping("/sendWithAttachment")
//    public ResponseEntity<String> sendEmailWithAttachment(@ModelAttribute @Valid EmailFiletDTO emailRequest) {
//        try {
//            if (emailRequest.file() == null) {
//                return ResponseEntity.status(400).body("File is null");
//            }
//
//            // 1. Subir el archivo a Cloudinary
//            String fileUrl = cloudinaryService.uploadFile(emailRequest.file());
//
//            // 2. Descargar el archivo desde Cloudinary
//            File downloadedFile = cloudinaryService.downloadFileFromCloudinary(fileUrl);
//
//            // 3. Enviar el correo con el archivo adjunto
//            for (String recipient : emailRequest.toUser()) {
//                emailService.sendEmailWithFile(recipient, emailRequest.subject(), emailRequest.message(), downloadedFile);
//            }
//
//            downloadedFile.delete();
//
//            return ResponseEntity.ok("Email sent successfully with attachment to " + emailRequest.toUser());
//        } catch (Exception ex) {
//            return ResponseEntity.status(500).body("Error sending email: " + ex.getMessage());
//        }
//    }
//
//
//}
//
