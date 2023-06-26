package com.yhdatabase.smartdata.datasource.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import com.yhdatabase.smartdata.datasource.repository.DataProcessRepository;
//import com.yhdatabase.smartdata.datasource.repository.OutPutTableRepository;
import com.yhdatabase.smartdata.datasource.repository.ProgMstRepository;
//import com.yhdatabase.smartdata.datasource.repository.ProgWorkFlowMngRepository;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    @Bean
    public ProgMstRepository progMstRepository() { return new ProgMstRepository(dataSource); }
}
