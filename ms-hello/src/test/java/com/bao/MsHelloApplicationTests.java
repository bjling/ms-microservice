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
			message.setSubject("短信业务申请");
			message.setText("<html>\n" +
					"\t<head>\n" +
					"\t<meta http-equiv='content-type' content='text/html; charset=UTF-8'> </head>\n" +
					"\t<body>\n" +
					"\t\thi:\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;部门：科技部\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;姓名：鲍春雨\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;邮箱：baochunyu@ppdai.com\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;申请业务名：催收\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;日预估量：10000\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;模板：这是催收短信{msg}。\n" +
					"\t\t<br>\n" +
					"\t\t<br>\n" +
					"\t\t&nbsp;&nbsp;&nbsp;&nbsp;请予以处理。\n" +
					"\t</body>\n" +
					"</html>","UTF-8","html");

			mailSender.send(message);
		}
		catch (Exception ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}

	}

}
