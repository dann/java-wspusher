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
