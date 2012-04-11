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
package com.github.dann.wspusher.test.common.util;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dann.wspusher.common.util.WebSocketUtils;
import com.github.dann.wspusher.exception.WsRuntimeException;

public class TestWebSocketClient {
  private static Logger logger = LoggerFactory.getLogger(TestWebSocketClient.class);
  private WebSocket.OnTextMessage onMessageListener;
  private WebSocketClient client;
  private WebSocket.Connection connection;

  public TestWebSocketClient(WebSocket.OnTextMessage onMessagelistner) throws Exception {
    this.client = createWebSocketClient();
    this.onMessageListener = onMessagelistner;
  }

  public void sendMessage(String endpointURL, String message) {
    this.connection = createConnection(client, endpointURL);
    sendMessage(endpointURL, message, this.connection);
  }

  private void sendMessage(String endpointURL, String message,
      WebSocket.Connection websocketConnection) {
    try {
      websocketConnection.sendMessage(message);
    } catch (IOException e) {
      throw new WsRuntimeException("Sending message failed " + endpointURL, e);
    }
  }

  private WebSocketClient createWebSocketClient() throws Exception {
    WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
    webSocketClientFactory.start();
    WebSocketClient client = webSocketClientFactory.newWebSocketClient();
    return client;
  }

  private Connection createConnection(WebSocketClient client, String endpointURL) {
    try {
      Future<Connection> futureConnection =
          client.open(new URI(endpointURL), this.onMessageListener);
      Connection connection = futureConnection.get();
      return connection;
    } catch (Exception e) {
      throw new WsRuntimeException("Failed connecting to " + endpointURL, e);
    }
  }

  public void shutdown() {
    WebSocketUtils.closeQuietly(this.connection);
  }

}
