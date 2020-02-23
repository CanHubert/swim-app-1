package com.can.app.swim.swimapp.helpers.email;

import com.can.app.swim.swimapp.entity.EmailTemplate;
import com.can.app.swim.swimapp.enums.EmailName;
import com.can.app.swim.swimapp.repository.EmailTemplateRepository;
import com.can.app.swim.swimapp.services.EmailService;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Optional;

@Component
public class MailSender implements IEmailSender{

    private EmailTemplateRepository emailTemplateRepository;
    private EmailService emailService;

    public MailSender(EmailTemplateRepository emailTemplateRepository, EmailService emailService){
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailService = emailService;
    }


    @Override
    public void sendEmail(EmailName name, EmailReceiver receiver) throws MessagingException {
        Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findByName(name.getTemplateName());
        if(emailTemplate.isPresent())
        {
            MailValues mailValues = new MailValues(name, receiver);
            emailService.sendHtmlVelocityMail(receiver.getUser().getEmail(), emailTemplate.get(), mailValues);
        }
    }

}
