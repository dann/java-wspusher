package com.github.dann.wspusher.common;

// FIXME Make configurable
public final class WsConfig {
  private static final String DEFAULT_RABBITMQ_SERVER_HOST = "localhost";

  public static final String getMQServerHost() {
    return DEFAULT_RABBITMQ_SERVER_HOST;
  }
}
