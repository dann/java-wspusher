package com.github.dann.wspusher.pubsub.subscriber;

import org.springframework.stereotype.Component;

import com.github.dann.wspusher.websocket.DataPusherWebSocket;

@Component
public interface Subscriber {

  public void subscribe(String channel, DataPusherWebSocket webSocket);

}
