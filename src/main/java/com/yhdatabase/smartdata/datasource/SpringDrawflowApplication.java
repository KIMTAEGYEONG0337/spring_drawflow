package com.yhdatabase.smartdata.datasource;

import com.yhdatabase.smartdata.datasource.config.JdbcTemplateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootApplication
@Import(JdbcTemplateConfig.class)
public class SpringDrawflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDrawflowApplication.class, args);
    }

}
