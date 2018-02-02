package com.bao.util;

/**
 * Created by baochunyu on 2017/3/7.
 */

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendMail {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        System.out.println("test==begin");
        String to = "changxianwei@yupaopao.cn";
        String from = "baochunyu@yupaopao.cn";
//        final String userName = "baochunyu@yupaopao.cn";
        final String password = "Aa10051544";
        String host = "smtp.exmail.qq.com";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");//这里的startls语句从properties当中拿掉也没有问题。想问一下大家，这句的作用是什么。
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.port", "465");//根据http://help.163.com/10/0731/11/6CTUBPT300753VB8.html，非SSL协议的端口为25
//
//        // 获取会话对象
//        Session session = Session.getInstance(props, new Authenticator() {
//
////Authenticator类是一个抽象类{
//            //这个getPasswordAuthentication()原本是return null的。
//            //需要重写这个方法，也是这个类里面最重要的一个方法。
//
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(userName, password);
//            }
//        });
//
//        try {//以下部分是在定义你的邮件内容。
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(to));
//            message.setSubject("Nice to meet you, JavaMail!");
//            message.setText("I have a dream!!!");
//            System.out.println("test==ing");
//            Transport.send(message);
//            System.out.println("发送成功！");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }



        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.exmail.qq.com");
//        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.port", "465");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject("test 主题");
        StringBuilder builder = new StringBuilder();
        builder.append("你好 hello");
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress(from));

        Transport transport = session.getTransport();
        transport.connect("smtp.exmail.qq.com", from, password);

        transport.sendMessage(msg, new Address[] { new InternetAddress(to) });
        transport.close();

    }
}
