package com.bao;

import com.bao.annotation.Eat;
import com.bao.executor.ExecutorPool;
import com.bao.task.ListTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MsHelloApplicationTests {

	@Autowired
	Eat eat;

	@Autowired
	ExecutorPool executorPool;

	@Autowired
	JavaMailSender mailSender;

	@Test
	public void contextLoads() {
		String a = eat.eating();
		System.out.println(a);

		ListTask task = new ListTask();

	}

	@Test
	public void emailTest(){


		try{
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress("baochunyu1987@163.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("baochunyu@ppdai.com"));
			message.setSubject("apply申请");
			message.setText("I have a dream ，我要申请");

			mailSender.send(message);
		}
		catch (Exception ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}

	}

}
