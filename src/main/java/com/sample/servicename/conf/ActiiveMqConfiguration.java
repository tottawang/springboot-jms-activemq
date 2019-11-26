package com.sample.servicename.conf;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiiveMqConfiguration {

  @Bean("activemqVirtualTopicQueue")
  public ActiveMQQueue activemqVirtualTopicQueue() throws UnknownHostException {
    // change the virtual topic name
    String virtualTopicName = "myTestFileuploadVirtualTopic";
    return new ActiveMQQueue(String.format("Consumer.%s.VirtualTopic.%s",
        InetAddress.getLocalHost().getCanonicalHostName() + "_springboot", virtualTopicName));
  }

  @Bean("activemqConnFactory")
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    return createActiveMQConnectionFactory("tcp://localhost:61616", ActiveMQConnection.DEFAULT_USER,
        ActiveMQConnection.DEFAULT_PASSWORD);
  }

  private ActiveMQConnectionFactory createActiveMQConnectionFactory(String brokerURL,
      String userName, String password) {
    ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory();
    mqConnectionFactory.setBrokerURL(brokerURL);
    mqConnectionFactory.setUserName(userName);
    mqConnectionFactory.setPassword(password);
    return mqConnectionFactory;
  }
}
