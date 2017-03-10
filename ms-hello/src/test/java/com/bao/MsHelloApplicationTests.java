package com.bao;

import com.bao.annotation.Eat;
import com.bao.executor.ExecutorPool;
import com.bao.task.ListTask;
import com.bao.util.CommandHelloWorld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

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

	@Test
	public void testSynchronous() {
		assertEquals("Hello World!", new CommandHelloWorld("World").execute());
		assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
	}

	@Test
	public void testAsynchronous1() throws Exception {
		assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
		assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
	}

	@Test
	public void testAsynchronous2() throws Exception {

		Future<String> fWorld = new CommandHelloWorld("World").queue();
		Future<String> fBob = new CommandHelloWorld("Bob").queue();

		assertEquals("Hello World!", fWorld.get());
		assertEquals("Hello Bob!", fBob.get());
	}

	@Test
	public void testObservable() throws Exception {

		Observable<String> fWorld = new CommandHelloWorld("World").observe();
		Observable<String> fBob = new CommandHelloWorld("Bob").observe();

		// blocking
		assertEquals("Hello World!", fWorld.toBlocking().single());
		assertEquals("Hello Bob!", fBob.toBlocking().single());

		// non-blocking
		// - this is a verbose anonymous inner-class approach and doesn't do assertions
		fWorld.subscribe(new Observer<String>() {

			@Override
			public void onCompleted() {
				// nothing needed here
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(String v) {
				System.out.println("onNext: " + v);
			}

		});

		// non-blocking
		// - also verbose anonymous inner-class
		// - ignore errors and onCompleted signal
		fBob.subscribe(new Action1<String>() {

			@Override
			public void call(String v) {
				System.out.println("onNext: " + v);
			}

		});

		// non-blocking
		// - using closures in Java 8 would look like this:

		//            fWorld.subscribe((v) -> {
		//                System.out.println("onNext: " + v);
		//            })

		// - or while also including error handling

		//            fWorld.subscribe((v) -> {
		//                System.out.println("onNext: " + v);
		//            }, (exception) -> {
		//                exception.printStackTrace();
		//            })

		// More information about Observable can be found at https://github.com/Netflix/RxJava/wiki/How-To-Use

	}

}
