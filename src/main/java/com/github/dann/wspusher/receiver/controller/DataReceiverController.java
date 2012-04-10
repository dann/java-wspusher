package com.github.dann.wspusher.receiver.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.dann.wspusher.common.WsConstants;
import com.github.dann.wspusher.common.util.IOUtils;
import com.github.dann.wspusher.websocket.DataPusher;

@Controller
@RequestMapping("/receiver")
public class DataReceiverController {

  private static Logger logger = LoggerFactory.getLogger(DataReceiverController.class);

  private DataPusher dataPusher;

  @Autowired
  public DataReceiverController(DataPusher pusher) {
    this.dataPusher = pusher;
  }

  @RequestMapping(method = RequestMethod.POST)
  public void receive(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String data = null;
    try {
      data = IOUtils.toString(request.getInputStream());
      if (logger.isDebugEnabled()) {
        logger.debug("Received json! json content: {}", data);
      }
    } catch (IOException e) {
      logger.error("Failed converting the http request to string object.", e);
      response.setStatus(HttpStatus.BAD_REQUEST_400);
      return;
    }

    try {
      pushDataToWebSocketClients(WsConstants.EXCHANGE_NAME, data);
    } catch (Exception e) {
      logger.error("Failed sending data to clients.", e);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
      return;
    }
  }

  private void pushDataToWebSocketClients(String channel, String oscilloscopeData) {
    dataPusher.pushDataToClients(channel, oscilloscopeData);
  }

}
