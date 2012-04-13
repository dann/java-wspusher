/*
 * Copyright 2012 Dann.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.dann.wspusher.websocket;

import junit.framework.Assert;

import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dann.wspusher.test.common.util.HttpPostTestDataFactory;
import com.github.dann.wspusher.test.common.util.TestHttpClient;
import com.github.dann.wspusher.test.common.util.TestWebSocketClient;

public class DataPusherWebSocketServletTest {
  private static Logger logger = LoggerFactory.getLogger(DataPusherWebSocketServletTest.class);

  // FIXME Make configurable using property file
  private static final int websocktServerPort = 7777;
  private static final String DATA_RECEIVER_ENDPOINT = "http://localhost:" + websocktServerPort
      + "/wspusher/receiver";

  private static final String DATA_PUSHER_WEB_SOCKET_ENDPOINT = "ws://localhost:"
      + websocktServerPort + "/wspusher/websocket";

  private String actualResultMessage;

  @Test
  public void testPushDataWithWebSocket() throws Exception {
    for (int i = 0; i < 100; i++) {
      doTest();
    }
    Thread.sleep(50);
  }

  private void doTest() throws Exception {
    TestWebSocketClient webSocketClient = null;
    try {
      webSocketClient = createTestWebSocketClient();
      webSocketClient.sendMessage(DATA_PUSHER_WEB_SOCKET_ENDPOINT, "Hello I'm web socket client");

      // Emulate micon
      String oscilloscopeData = HttpPostTestDataFactory.createTestData();
      TestHttpClient httpClient = createTestHttpClient();
      httpClient.postData(DATA_RECEIVER_ENDPOINT, oscilloscopeData);

      if (logger.isDebugEnabled()) {
        logger.debug("actualResultMessage={}", actualResultMessage);
      }

      Assert.assertNotNull(actualResultMessage);
    } catch (Exception e) {
      logger.error("error occured", e);
      throw e;
    } finally {
      if (webSocketClient != null) {
        webSocketClient.shutdown();
      }
    }
  }

  private TestWebSocketClient createTestWebSocketClient() throws Exception {
    TestWebSocketClient webSocketClient = createWebSocketClient();
    return webSocketClient;
  }


  private TestWebSocketClient createWebSocketClient() throws Exception {
    WebSocket.OnTextMessage messageListener = new WebSocket.OnTextMessage() {

      @Override
      public void onMessage(String data) {
        if (logger.isDebugEnabled()) {
          logger.debug("onMessage: received data {}", data);
        }
        actualResultMessage = data;
      }

      @Override
      public void onOpen(Connection connection) {
        if (logger.isDebugEnabled()) {
          logger.debug("Connection is opened!");
        }
      }

      @Override
      public void onClose(int closeCode, String message) {
        if (logger.isDebugEnabled()) {
          logger.debug("Connection is closed");
        }

      }
    };
    TestWebSocketClient webSocketClient = new TestWebSocketClient(messageListener);
    return webSocketClient;
  }

  private TestHttpClient createTestHttpClient() {
    TestHttpClient httpClient = new TestHttpClient();
    return httpClient;
  }

}
