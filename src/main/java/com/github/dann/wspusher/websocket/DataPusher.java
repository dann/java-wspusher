package com.github.dann.wspusher.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dann.wspusher.pubsub.publisher.Publisher;

@Component
public class DataPusher {
  static Logger logger = LoggerFactory.getLogger(DataPusher.class);
  private Publisher publisher;

  @Autowired
  public DataPusher(Publisher publisher) {
    this.publisher = publisher;
  }

  public void pushDataToClients(String channel, String data) {
    if (logger.isDebugEnabled()) {
      logger.debug("Publishing data to client. channel is {}, data is {}", new Object[] {channel,
          data});
    }
    publisher.publish(channel, data);
  }

}
