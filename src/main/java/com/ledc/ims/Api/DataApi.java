package com.ledc.ims.Api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataApi {
    @RequestMapping("/login")
    public String hello(){
        return "Api Test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    }
}
