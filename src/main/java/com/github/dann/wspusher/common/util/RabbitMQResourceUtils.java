package com.github.dann.wspusher.common.util;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public final class RabbitMQResourceUtils {
  private RabbitMQResourceUtils() {

  }

  public static void closeQuietly(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (IOException e) {
        // ignore
      }
    }
  }

  public static void closeQuietly(Channel channel) {
    if (channel != null) {
      try {
        channel.close();
      } catch (IOException e) {
        // ignore
      }
    }
  }
}
