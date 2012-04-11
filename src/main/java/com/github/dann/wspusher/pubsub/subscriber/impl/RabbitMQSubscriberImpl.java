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
package com.github.dann.wspusher.pubsub.subscriber.impl;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.dann.wspusher.common.util.RabbitMQResourceUtils;
import com.github.dann.wspusher.exception.WsRuntimeException;
import com.github.dann.wspusher.pubsub.subscriber.Subscriber;
import com.github.dann.wspusher.websocket.DataPusherWebSocket;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

@Component
public class RabbitMQSubscriberImpl implements Subscriber {
  private static Logger logger = LoggerFactory.getLogger(RabbitMQSubscriberImpl.class);
  private static final String DEFAULT_EXCHANGE_TYPE = "fanout";

  public RabbitMQSubscriberImpl() {}

  @Override
  public void subscribe(String exchange, DataPusherWebSocket webSocket) {
    Connection connection = null;
    Channel channel = null;

    try {
      // FIXME Cache connection!
      ConnectionFactory factory = new ConnectionFactory();
      connection = factory.newConnection();
      channel = connection.createChannel();

      channel.exchangeDeclare(exchange, DEFAULT_EXCHANGE_TYPE);
      String queueName = channel.queueDeclare().getQueue();
      channel.queueBind(queueName, exchange, "");
      QueueingConsumer consumer = new QueueingConsumer(channel);
      channel.basicConsume(queueName, true, consumer);

      doWork(consumer, webSocket);

    } catch (Exception e) {
      logger.error("Error occured", e);
      throw new WsRuntimeException(e);

    } finally {
      RabbitMQResourceUtils.closeQuietly(channel);
      RabbitMQResourceUtils.closeQuietly(connection);
    }

  }

  private void doWork(QueueingConsumer consumer, DataPusherWebSocket webSocket) {
    if (logger.isInfoEnabled()) {
      logger.info("Waiting for messages...");
    }

    while (true) {
      try {
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String message = new String(delivery.getBody());
        logger.info("Subscriber received message:" + message);
        processMessage(webSocket, message);
      } catch (InterruptedException e) {
        logger.warn("Stopping waitng message from the topic", e);
        throw new WsRuntimeException("Stopping waiting message from the topic", e);
      } catch (IOException e) {
        throw new WsRuntimeException("Sending message failed", e);
      }
    }
  }

  private void processMessage(DataPusherWebSocket webSocket, String message) throws IOException {
    webSocket.getConnection().sendMessage(message);
  }
}
