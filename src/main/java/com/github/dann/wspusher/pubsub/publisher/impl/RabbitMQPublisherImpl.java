/*
 * Copyright 2012 Dann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
