package com.example.CartGalaxy.common.service;

import com.example.CartGalaxy.common.model.SendEmailDTO;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(SendEmailDTO emailDTO) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailDTO.getRecipient());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getText());

        mailSender.send(mailMessage);
    }
}
