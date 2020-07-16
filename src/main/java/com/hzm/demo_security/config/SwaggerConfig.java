package com.hzm.demo_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.P;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hzm
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        List<Parameter> parameters = new ArrayList<Parameter>();
        Parameter parameter_authorization = new ParameterBuilder().name("Authorization").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(parameter_authorization);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzm.demo_security"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("spring demo doc")
                .description("一个Springboot整合SpringSecurity的demo")
                .contact(new Contact("hzm","https://www.baidu.com","2696333036@qq.com"))
                .version("1.0")
                .build();
    }
}
