package com.sunflower.web;

/**
 * Created by yurariznyk&romantsimura on 22.11.14.
 */

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

public class MailServer {

    private static Logger logger = Logger.getLogger(MailServer.class);

    protected static void messageAfterRegistration(String name, String password, String email, String login){

        String msg="<html> <body> Hi "+name+", <p style='color:green;'> " +
                "Regards for registrating,<br/> " +
                "Your user login:"+login+"<br/> " +
                "password:"+password+"<br/>" +
                "Respectfully,<br/> " +
                "Accounts Team SunFlower.</p>" +
                "</body> " +
                "</html>";
        sendMessage(email, msg,"Registration on SunFlower");
    }

  /*  protected static void messagePasswordReminder(String email){
        Map<String, String> rootMap = new HashMap<String, String>();//here are fields, which we wanted to paste into template
        rootMap.put("name", "name from database");
        rootMap.put("login", email);
        rootMap.put("password", "password from db");
       sendMessage(email, rootMap, "mail-reminder-password-template.ftl","SunFlower remind your password");
    }*/

//general mail configuration for all templates
    private static void sendMessage(String email, String msg, String messageTitle){
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("nc.sunflower.2014@gmail.com", "moonflower2014");//server e-mail and password
                    }
                });
        try {
            Message message = new MimeMessage(session);
           InternetAddress[] address = {new InternetAddress(email)};
            String from="nc.sunflower.2014@gmail.com";
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,address);
            message.setSubject(messageTitle);
            message.setContent(msg, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        }
    }
    //reading template file and store into String
    private static String readFile(String fileName)
    {
        String res = "";
        try {
            BufferedReader fin = new BufferedReader(new FileReader(fileName));
            String s;
            while((s=fin.readLine())!=null)
                res+=s+'\n';
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return res;
    }


}
