package org.music.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

  @Test
  void sendMail() {
    Email email=new Email();
    String from="jogendertemp@gmail.com";
    String to="jogendershekhawat425@gmail.com";
    String subject="test";
    String body="Email functionality working";
    email.sendMail(to,from,body,subject);
  }
}