package org.music.util;


import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;

public class Email {

  public void sendMail(String to,String from, String bodyMessage, String subject){

    Authenticator authenticator=new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from,"XXXX");
      }
    };
    Properties prop=new Properties();
    prop.put("mail.smtp.host","smtp.gmail.com");
    prop.put("mail.smtp.port",465);
    prop.put("mail.smtp.auth",true);
    prop.put("mail.smtp.starttls.required",true);
    prop.put("mail.smtp.starttls.enable",true);
    prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

    //getting session object
    Session session=Session.getInstance(prop,authenticator);

    //getting message object
    Message message=new MimeMessage(session);

    //from address to address
    Address fromAddress=null;
    Address toAddress=null;
    try {
       fromAddress=new InternetAddress(from);
       toAddress=new InternetAddress(to);
    } catch (AddressException e) {
      e.printStackTrace();
    }

    try {
      message.setFrom(fromAddress);
      message.addRecipient(Message.RecipientType.TO,toAddress);
      message.setSubject(subject);
      message.setContent(bodyMessage,"text/html");
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    //send message
    try {
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
