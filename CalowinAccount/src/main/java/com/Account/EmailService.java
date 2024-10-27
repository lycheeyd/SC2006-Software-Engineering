package com.Account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.service.username}")
    private String username;

    @Value("${email.service.password}")
    private String password;

    @Value("${email.service.host}")
    private String host;

    @Value("${email.service.port}")
    private String port;

    public void sendEmail(String recipient, String subject, String messageBody) {
        // SMTP server configuration
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create a session with the SMTP server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));  // no-reply email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient)); // recipient email
            message.setSubject(subject);  // email subject
            message.setText(messageBody); // email body

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
