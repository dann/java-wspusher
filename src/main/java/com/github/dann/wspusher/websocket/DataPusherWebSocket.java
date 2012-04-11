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
package com.github.dann.wspusher.websocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.dann.wspusher.common.WsConstants;
import com.github.dann.wspusher.common.util.WebSocketUtils;
import com.github.dann.wspusher.pubsub.subscriber.Subscriber;

@Component
@Scope("prototype")
public class DataPusherWebSocket implements WebSocket.OnTextMessage {
  private static Logger logger = LoggerFactory.getLogger(DataPusherWebSocket.class);

  private Subscriber subscriber;
  private Connection connection = null;

  @Autowired
  public DataPusherWebSocket(Subscriber subscriber) {
    this.subscriber = subscriber;
  }

  public Connection getConnection() {
    return connection;
  }

  void setConnection(Connection connection) {
    this.connection = connection;
  }

  public void onClose(int closeCode, String message) {
    if (logger.isDebugEnabled()) {
      logger.debug("Close connection. closeCode{}, message{}", new Object[] {closeCode, message});
    }
    WebSocketUtils.closeQuietly(connection);
    this.connection = null;
  }

  public void onOpen(Connection connection) {
    if (logger.isDebugEnabled()) {
      logger.debug("Start connection");
    }
    try {
      this.setConnection(connection);
      this.subscriber.subscribe(WsConstants.EXCHANGE_NAME, this);

    } catch (Exception e) {
      logger.error("Failed subscripting to {}", WsConstants.EXCHANGE_NAME);
      WebSocketUtils.closeQuietly(connection);
      this.connection = null;
    }
  }

  public void onMessage(String message) {
    if (logger.isDebugEnabled()) {
      logger.debug("Message is received. The mesage from client: {}", message);
    }
  }

}
