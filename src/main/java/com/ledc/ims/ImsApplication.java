package com.ledc.ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication

public class ImsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImsApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> restServlet() {
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext
                = new AnnotationConfigWebApplicationContext();
        //base package
        applicationContext.scan("com.ledc.ims.Api");
        //通过构造函数指定dispatcherServlet的上下文
        DispatcherServlet rest_dispatcherServlet
                = new DispatcherServlet(applicationContext);
        //用ServletRegistrationBean包装servlet
        ServletRegistrationBean<DispatcherServlet> registration
                = new ServletRegistrationBean<>(rest_dispatcherServlet,"/v2/api-ims/*");
        registration.setName("rest");
        registration.setLoadOnStartup(1);
        //registration.getUrlMappings().clear();
        //registration.addUrlMappings("/rest/*");
        registration.addUrlMappings("/v2/api-ims/*");
        return registration;
    }

}
