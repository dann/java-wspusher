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
