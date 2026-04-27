package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.MailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    private final JavaMailSender mailSender;
    
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void send(MailDTO mailDTO) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(buildEmail(mailDTO.firstName(), mailDTO.link()), true);
            helper.setTo(mailDTO.email());
            helper.setSubject("Confirm your email");
            mailSender.send(mimeMessage);
            LOGGER.info("{} received email", mailDTO.email());
        }
        catch (MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
    
    private String buildEmail(String name, String link) {
        try {
            ClassPathResource resource = new ClassPathResource(
                    "templates/confirmation_email.html");
            String html = new String(resource.getInputStream().readAllBytes());
            html = html.replace("{{name}}", name);
            html = html.replace("{{link}}", link);
            
            return html;
        }
        catch (IOException e) {
            throw new IllegalStateException("Cannot load email template");
        }
    }
    
}

