package com.czj.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by 11273 on 2018-2-26.
 */
public class EmailUtil {

    //发件人账号
    static private String sendEmail = "zengjin_chen@163.com";
    static private String emailSubject = "日志监控系统报警！";


    static public void sendEmail(String userEmail,String content) throws MessagingException {
        Properties props = new Properties();//环境变量设置。发送邮件时才需要
        props.setProperty("mail.transport.protocol", "smtp");//发送使用的协议
        props.setProperty("mail.host", "smtp.163.com");//发送服务器的主机地址
        //props.setProperty("mail.host", "123.125.50.133");
        props.setProperty("mail.smtp.auth", "true");//请求身份验证
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);//代表一封邮件

        message.setFrom(new InternetAddress(sendEmail));//设置发件人
        message.setRecipients(Message.RecipientType.TO, userEmail);//设置收件人
        message.setSubject(emailSubject);//设置主题

        //设置邮件的正文内容
        message.setText(content);
        message.saveChanges();
        //发送邮件
        Transport ts = session.getTransport();//得到火箭
        ts.connect("zengjin_chen","***");//连接,/应填用户名和授权码
        ts.sendMessage(message, message.getAllRecipients());

        ts.close();
    }
}