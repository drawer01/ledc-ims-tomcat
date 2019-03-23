package com.ledc.ims.Api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api-ims")
public class DataApi {
    @RequestMapping("/home")
    public String hello(){
        return "Api Test>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    }
}
