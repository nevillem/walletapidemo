package com.walletapidemo.walletapidemo.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.walletapidemo.walletapidemo.requests.EmailDetails;

import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    public void sendEmail(EmailDetails emailDetails) throws Exception{
        try {
        MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailSender);
            helper.setTo(emailDetails.getRecipient());
            helper.setText(emailDetails.getMsgBody(), true);
            helper.setSubject(emailDetails.getSubject());
            javaMailSender.send(message);
            System.out.println("Mail sent successfully");
        }catch (MailException exception){
            exception.getStackTrace();
        }
    }
}
