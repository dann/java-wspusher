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
