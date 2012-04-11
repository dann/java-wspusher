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
