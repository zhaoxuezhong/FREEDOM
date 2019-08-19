package com.zxz.freedombase.controller;

import com.zxz.freedomlogging.annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class LogController {

    private Logger logger = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "test";
    }

}
