package com.bao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.config.yaml.api.YamlShardingDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by baochunyu on 2017/3/12.
 */
@Configuration
public class YamlJdbcConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        File yamlFile = new File("/Users/baochunyu/IdeaProjects/ms-microservice/ms-sharding/src/main/resources/data-source.yml");
        DataSource dataSource = new YamlShardingDataSource(yamlFile);
        return dataSource;
    }

}
