package com.github.dann.wspusher.pubsub.publisher;

import org.springframework.stereotype.Component;

@Component
public interface Publisher {
  public void publish(String channel, String data);
}
