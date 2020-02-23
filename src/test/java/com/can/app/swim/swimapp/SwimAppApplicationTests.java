package com.can.app.swim.swimapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SwimAppApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Test @Autowired
//	void testEmailSender(EmailTemplateRepository emailTemplateRepository, EmailService emailService){
//		MailSender sender = new MailSender(emailTemplateRepository, emailService);
//		User user = new User("Hubert", "Can", "canhubert@gmail.com", true);
//		EmailReceiver receiver = new EmailReceiver(user);
//		sender.sendEmail(EmailName.WELCOME, receiver);
//	}

}
