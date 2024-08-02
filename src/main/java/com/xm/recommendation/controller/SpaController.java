package com.xm.recommendation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controller for redirection non-api requests to the default react page. */
@Controller
public class SpaController {

  @RequestMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/index.html";
  }
}

