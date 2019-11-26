package com.sample.servicename.conf;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

@Configuration
public class SpringJmsConfiguration {

  @Bean("messageListenerContainer")
  @Autowired
  public DefaultMessageListenerContainer defaultMessageListenerContainer(
      @Qualifier("activemqConnFactory") ConnectionFactory connectionFactory,
      @Qualifier("customMessageListener") MessageListener messageListener,
      @Qualifier("activemqVirtualTopicQueue") ActiveMQQueue destination) {
    DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
    dmlc.setConnectionFactory(connectionFactory);
    MessageListenerAdapter listenerAdapter = new MessageListenerAdapter();
    listenerAdapter.setDelegate(messageListener);
    listenerAdapter.setDefaultListenerMethod("onMessage");
    dmlc.setMessageListener(listenerAdapter);
    dmlc.setDestination(destination);
    // AbstractFileUploadListener handles ack manually rather than AUTO_ACKNOWLEDGE, which
    // guarantees a message is not acknowledged until file upload is completely done
    dmlc.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
    dmlc.setConcurrentConsumers(10);
    return dmlc;
  }

}
