package com.sample.servicename.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component("customMessageListener")
public class CustomMessageListener implements MessageListener {

  @Override
  public void onMessage(Message message) {
    try {
      System.out.println(String.format("received: %s", message));
    } finally {
      acknowledge(message);
    }
  }

  private void acknowledge(Message message) {
    try {
      message.acknowledge();
    } catch (JMSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
