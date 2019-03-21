package com.ledc.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enableUrlTemplating(false)
                .select()
                // 当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.ledc.ims.Api"))
                .paths(PathSelectors.any())
                .build();

    }
    //构建api文档的详细信息函数
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                //页面标题
                .title("智能监控系统 RESTful API")
                //创建人
                .contact(new Contact(
                        "SELLIN.CHU",
                        "http://www.ledc.com",
                        "rd01@drawerkvm.cn"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}
