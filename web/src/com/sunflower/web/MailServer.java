package com.sunflower.web;

/**
 * Created by yurariznyk&romantsimura on 22.11.14.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailServer {
    protected static void messageAfterRegistration(String name, String password, String email){
        Map<String, String> rootMap = new HashMap<String, String>();//here are fields, which we wanted to paste into template
        rootMap.put("name", name);
        rootMap.put("login", login);
        rootMap.put("password", password);
        sendMessage(email, rootMap, "mail-registration-template.ftl","Registration on SunFlower");
    }

    protected static void messagePasswordReminder(String email){
        Map<String, String> rootMap = new HashMap<String, String>();//here are fields, which we wanted to paste into template
        rootMap.put("name", "name from database");
        rootMap.put("login", email);
        rootMap.put("password", "password from db");
       sendMessage(email, rootMap, "mail-reminder-password-template.ftl","SunFlower remind your password");
    }

//general mail configuration for all templates
    private static void sendMessage(String email, Map<String, String> rootMap, String templateName, String messageTitle){
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(props,
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
            message.setContent(makeTemplate(rootMap, readFile(templateName)), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    //putting fields into template file
    private static String makeTemplate(Map<String, String> map, String s)
    {
        String field ="";
        String res = "";
        for(int i =0; i<s.length(); ++i){
            if(s.charAt(i)=='$'){
                i+=2;
                while(s.charAt(i)!='}')
                {
                    field+=s.charAt(i);
                    ++i;
                }
                ++i;
                res+=map.get(field);
                field="";
            }
            res+=s.charAt(i);
        }
        return res;
    }
}
