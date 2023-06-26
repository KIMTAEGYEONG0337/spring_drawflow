package com.yhdatabase.smartdata.datasource.config;

import com.yhdatabase.smartdata.datasource.repository.DataProcessRepository;
import com.yhdatabase.smartdata.datasource.repository.OutPutTableRepository;
import com.yhdatabase.smartdata.datasource.repository.ProgWorkFlowMngRepository;
import lombok.Data;
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

    @Bean
    public ProgWorkFlowMngRepository progWorkFlowMngRepository() { return new ProgWorkFlowMngRepository(dataSource); }

    @Bean
    public DataProcessRepository onlineTransIsolRepository() { return new DataProcessRepository(dataSource); }

    @Bean
    public OutPutTableRepository outPutTableRepository() { return new OutPutTableRepository(dataSource); }
}
