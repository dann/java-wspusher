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

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataPusherWebSocketServlet extends AbstractSpringWebSocketServlet {
  private static Logger logger = LoggerFactory.getLogger(DataPusherWebSocketServlet.class);

  private static final long serialVersionUID = 3092912355548885983L;

  @Override
  public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
    if (logger.isDebugEnabled()) {
      logger.debug("doWebSocketConnect start");
    }
    return createWebSocket();
  }

  private WebSocket createWebSocket() {
    return getApplicationContext().getBean(DataPusherWebSocket.class);
  }

}
