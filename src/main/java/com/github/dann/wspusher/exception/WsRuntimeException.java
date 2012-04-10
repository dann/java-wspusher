package com.github.dann.wspusher.exception;

public class WsRuntimeException extends RuntimeException {
  private static final long serialVersionUID = 7862129322909291650L;

  public WsRuntimeException() {
    super();
  }

  public WsRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public WsRuntimeException(String message) {
    super(message);
  }

  public WsRuntimeException(Throwable cause) {
    super(cause);
  }
}
