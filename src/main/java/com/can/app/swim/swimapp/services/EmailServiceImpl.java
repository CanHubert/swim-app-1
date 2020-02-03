package com.can.app.swim.swimapp.services;

import com.can.app.swim.swimapp.entity.EmailTemplate;
import com.can.app.swim.swimapp.helpers.MailValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class EmailServiceImpl implements EmailService {

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    @Override
    public void sendSimpleMessage(String to, EmailTemplate template) {
        sendSimpleMessage(to, template.getSubject(), template.getContent());
    }

    @Override
    public void sendHtmlMessage(String to, EmailTemplate template) throws MessagingException {
        sendHtmlMessage(to, template.getSubject(), template.getContent());
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlVelocityMail(String to, EmailTemplate template, MailValues properties) throws MessagingException {
        sendHtmlMessage(to, template.getSubject(), processProperties(template.getContent(), properties.getMap()));
    }

    private String processProperties(String content, Map<String,String> properties){
        for (String key : properties.keySet()) {
            content = content.replace("${" + key + "}", properties.get(key));
        }
        return content;
    }
}
