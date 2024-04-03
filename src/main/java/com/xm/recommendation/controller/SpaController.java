package com.xm.recommendation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controller for the default react page. */
@Controller
public class SpaController {

  @RequestMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/index.html";
  }
}
