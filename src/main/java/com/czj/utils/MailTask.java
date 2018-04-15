package com.czj.utils;

import javax.mail.MessagingException;

public class MailTask implements Runnable {
    private String userMail ;
    private String content ;

    public MailTask(){

    }
    public MailTask(String mail,String con){
        this.userMail = mail;
        this.content = con;
    }


    @Override
    public void run() {
        try {
            EmailUtil.sendEmail(userMail,content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
