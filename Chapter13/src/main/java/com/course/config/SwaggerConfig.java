package com.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  //依赖Spring注解
@EnableSwagger2
public class SwaggerConfig {

    @Bean  //依赖Spring注解
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())  //创建api的基本信息，如：标题、描述、版本等
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("UserManager service API")
                .contact(new Contact("zhu","","zhuhh2019@163.com"))
                .description("this is UserManager service API")
                .version("1.0")
                .build();
    }

}
