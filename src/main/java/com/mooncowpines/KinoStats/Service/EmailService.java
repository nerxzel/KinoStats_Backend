package com.mooncowpines.KinoStats.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

@Service
public class EmailService {

    private final SendGrid sendGrid;
    private final String fromEmail;

    public EmailService(@Value("${sendgrid.api-key}") String apiKey,
                        @Value("${sendgrid.from-email}") String fromEmail) {
        this.sendGrid = new SendGrid(apiKey);
        this.fromEmail = fromEmail;
    }

    public void sendResetCode(String to, String code) {
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        String subject = "Password Reset Code";
        Content content = new Content("text/plain",
            "Your password reset code is: " + code +
            "\n\nThis code expires in 15 minutes.");

        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid error: " + response.getBody());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}