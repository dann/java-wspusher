package com.github.dann.wspusher.pubsub.publisher.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.dann.wspusher.common.WsConfig;
import com.github.dann.wspusher.common.WsConstants;
import com.github.dann.wspusher.common.util.RabbitMQResourceUtils;
import com.github.dann.wspusher.exception.WsRuntimeException;
import com.github.dann.wspusher.pubsub.publisher.Publisher;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitMQPublisherImpl implements Publisher {
  private Logger logger = LoggerFactory.getLogger(RabbitMQPublisherImpl.class);

  private static final String EXCHANGE_TYPE = "fanout";


  public RabbitMQPublisherImpl() {}

  public void publish(String exchange, String message) {
    Connection connection = null;
    Channel channel = null;
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(WsConfig.getMQServerHost());
      connection = factory.newConnection();
      channel = connection.createChannel();
      channel.exchangeDeclare(exchange, EXCHANGE_TYPE);
      channel.basicPublish(exchange, "", null, message.getBytes(WsConstants.ENCODING_UTF8));

    } catch (Exception e) {
      logger.error("Publishing message faile", e);
      throw new WsRuntimeException("Publishig message failed", e);
    } finally {
      RabbitMQResourceUtils.closeQuietly(channel);
      RabbitMQResourceUtils.closeQuietly(connection);
    }

  }

}
