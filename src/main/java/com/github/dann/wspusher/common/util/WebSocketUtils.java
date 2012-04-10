package com.github.dann.wspusher.common.util;

import org.eclipse.jetty.websocket.WebSocket;

public final class WebSocketUtils {
  private WebSocketUtils() {}

  public static void closeQuietly(WebSocket.Connection connection) {
    if (connection != null) {
      connection.close();
    }
  }

}
