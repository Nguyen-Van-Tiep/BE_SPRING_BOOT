package com.example.demo.service;

import com.example.demo.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        Email email = new Email();
        email.setToEmail("khanhnn1@biplus.com.vn");
        email.setBody("adadsaads");
        email.setSubject("qqqqqqqqqq");
        emailService.sendSimpleMessage("khanhnn1@biplus.com.vn", "aaaaaaaaaa", "gggggggggggg");
    }

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }


    public void sendPasswordResetEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sdaghfsdiuf@gmail.com");
        message.setTo(email.getToEmail());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        emailSender.send(message);
    }
}


