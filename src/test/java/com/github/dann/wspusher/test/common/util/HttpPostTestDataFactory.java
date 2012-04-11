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
package com.github.dann.wspusher.test.common.util;

import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class HttpPostTestDataFactory {

  public static String createTestData() {
    JSONObject channel1 = createTimelineData();
    String jsonText = JSONValue.toJSONString(channel1);
    return jsonText;
  }

  private static JSONObject createTimelineData() {
    LinkedList<Double> timeline = new LinkedList<Double>();
    timeline.add(new Double(0.1));
    timeline.add(new Double(0.1));

    JSONObject channel1 = new JSONObject();
    channel1.put("channel1", timeline);
    return channel1;
  }
}
