package com.github.dann.wspusher.controller;

import java.io.IOException;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dann.wspusher.common.WsEndpoints;
import com.github.dann.wspusher.common.util.IOUtils;

@Controller
@RequestMapping(WsEndpoints.CONFIG_ENDOINT)
public class ConfigController {
  private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

  private static final String EMPTY_CONFIG = "{}";

  private Queue<String> configurationRequestsQueue = new ArrayQueue<String>();

  @RequestMapping(method = RequestMethod.POST)
  public void configure(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String config = IOUtils.toString(request.getInputStream());
    if (logger.isDebugEnabled()) {
      logger.debug("Received the configuration request: {}", config);
    }
    configurationRequestsQueue.add(config);
  }

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody
  String getConfig() {
    if (!configurationRequestsQueue.isEmpty()) {
      String config = configurationRequestsQueue.remove();
      if (logger.isDebugEnabled()) {
        logger.debug("Get configuration: {}", config);
      }

      return config;
    } else {
      return EMPTY_CONFIG;
    }
  }
}
