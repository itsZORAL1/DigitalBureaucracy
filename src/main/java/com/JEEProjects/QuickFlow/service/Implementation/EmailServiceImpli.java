package com.JEEProjects.QuickFlow.service.Implementation;

import com.JEEProjects.QuickFlow.service.Emailservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpli implements Emailservice {

    private final JavaMailSender javaMailSender;

    // Notez que le nom du constructeur a été corrigé pour correspondre au nom de la classe
    public EmailServiceImpli(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
    @Override
    public void sendSimpleMessageWithAttachment(String to, String subject, String text, byte[] pdfAttachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            // Attach the PDF file
            ByteArrayResource pdfResource = new ByteArrayResource(pdfAttachment);
            helper.addAttachment("legal.pdf", pdfResource, "application/pdf");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}