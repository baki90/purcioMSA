package com.order.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class CommonConfig {
    // for singleton
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // RequestMapping으로 할당된 모든 URL 리스트를 추출
                // 그중 /api/** 인 URL들만 필터링, 해당 부분은 우리가 api를 어떻게 설정하냐에 따라 다르게 하면 됨.
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }


}
