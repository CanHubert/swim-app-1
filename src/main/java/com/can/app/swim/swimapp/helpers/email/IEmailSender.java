package com.can.app.swim.swimapp.helpers.email;

import com.can.app.swim.swimapp.enums.EmailName;

import javax.mail.MessagingException;

@FunctionalInterface
public interface IEmailSender {

    void sendEmail(EmailName name, EmailReceiver receiver) throws MessagingException;
}
