package com.can.app.swim.swimapp.services;

import com.can.app.swim.swimapp.entity.EmailTemplate;
import com.can.app.swim.swimapp.helpers.MailValues;

import javax.mail.MessagingException;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String content);

    void sendSimpleMessage(String to, EmailTemplate template);

    void sendHtmlMessage(String to, EmailTemplate template) throws MessagingException;
    void sendHtmlMessage(String to, String subject, String content) throws MessagingException;

    void sendHtmlVelocityMail(String to, EmailTemplate template, MailValues properties) throws MessagingException;

}
