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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dann.wspusher.exception.WsRuntimeException;

public class TestHttpClient {
  private static Logger logger = LoggerFactory.getLogger(TestHttpClient.class);

  private static final int HTTP_SUCCESS = 200;
  private static final String ENCODING_UTF8 = "UTF-8";

  private HttpClient httpClient = null;

  public TestHttpClient() {
    httpClient = new DefaultHttpClient();
  }

  public void postData(String endpointURL, String data) {
    try {

      HttpPost post = createPostMethod(endpointURL, data);
      HttpResponse response = doPost(httpClient, endpointURL, post);
      int statusCode = response.getStatusLine().getStatusCode();
      if (logger.isDebugEnabled()) {
        logger.debug("Responsse from {}. satusCode is {}", new Object[] {endpointURL, statusCode});
      }

      if (HTTP_SUCCESS != statusCode) {
        HttpEntity responseEntity = response.getEntity();
        String errorResponse = EntityUtils.toString(responseEntity);
        logger.error("Sending request failed!!! status code:{}, Error response body: {}",
            new Object[] {statusCode, errorResponse});
        throw new WsRuntimeException("Sending request failed!!!!! status code is" + statusCode);
      }
    } catch (Exception e) {
      throw new WsRuntimeException("Posting message to {" + endpointURL + "} failed", e);
    } finally {
      if (httpClient != null) {
        httpClient.getConnectionManager().shutdown();
      }
    }
  }


  private HttpPost createPostMethod(String endpointURL, String data)
      throws UnsupportedEncodingException {
    HttpPost post = new HttpPost(endpointURL);
    post.setEntity(new ByteArrayEntity(data.getBytes(ENCODING_UTF8)));
    return post;
  }


  private HttpResponse doPost(HttpClient httpClient, String endpointURL, HttpPost post)
      throws IOException {
    try {
      HttpResponse response = httpClient.execute(post);
      return response;
    } catch (ConnectException e) {
      throw new WsRuntimeException("Failed connecting to " + endpointURL, e);
    } catch (ClientProtocolException e) {
      throw new WsRuntimeException("Failed connecting to " + endpointURL, e);
    }
  }

}
