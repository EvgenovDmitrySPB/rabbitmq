package com.leo.rabbithello.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private static Logger log = LogManager.getLogger();

    @ApiOperation("Test")
    @GetMapping("test")
    @ResponseBody
    public String test1() {
        return "Answer from the rabbitHello program";
    }

}
