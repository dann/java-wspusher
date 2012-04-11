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

import javax.servlet.ServletException;

import org.eclipse.jetty.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.dann.wspusher.common.WsConstants;

public abstract class AbstractSpringWebSocketServlet extends WebSocketServlet {
  private static Logger logger = LoggerFactory.getLogger(AbstractSpringWebSocketServlet.class);

  private static final long serialVersionUID = 7189529541971996159L;

  private ApplicationContext applicationContext;

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void init() throws ServletException {
    try {
      applicationContext = new ClassPathXmlApplicationContext("websocketApplicationContext.xml");
      super.init();
    } catch (Exception e) {
      logger.error("Failed loading applicationContext. Configuration path is "
          + WsConstants.CONFIG_WEBSOCKET_APPLICATION_CONTEXT, e);
      throw new ServletException(e);
    }
  }
}
