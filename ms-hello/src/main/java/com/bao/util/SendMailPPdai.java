package com.bao.util;

/**
 * Created by baochunyu on 2017/3/7.
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailPPdai {
    public static void main(String[] args) {
        String to = "wuyunge@ppdai.com";
        String from = "baochunyu@ppdai.com";
        final String userName = "baochunyu@ppdai.com";
        final String password = "Aa10051544";
        String host = "mail.ppdai.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//这里的startls语句从properties当中拿掉也没有问题。想问一下大家，这句的作用是什么。
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");//根据http://help.163.com/10/0731/11/6CTUBPT300753VB8.html，非SSL协议的端口为25

        // 获取会话对象
        Session session = Session.getInstance(props, new Authenticator() {

//Authenticator类是一个抽象类{
            //这个getPasswordAuthentication()原本是return null的。
            //需要重写这个方法，也是这个类里面最重要的一个方法。

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {//以下部分是在定义你的邮件内容。
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Nice to meet you, JavaMail!");
            message.setText("I have a dream!!!");

            Transport.send(message);
            System.out.println("发送成功！");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
