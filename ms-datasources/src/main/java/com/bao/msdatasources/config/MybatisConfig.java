package com.bao.msdatasources.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by nannan on 2017/6/5.
 */
@Configuration
public class MybatisConfig {

    @Autowired
    Environment environment;

    @Value("${multi.datasource}")
    private List<String> datasources;


    private DataSource dataSource(String name) {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty(name + ".datasource.driverClassName"));
        props.put("url", environment.getProperty(name + ".datasource.url"));
        props.put("username", environment.getProperty(name + ".datasource.username"));
        props.put("password", environment.getProperty(name + ".datasource.password"));
        try {
            return DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        datasources.forEach(node -> {
            DataSource dataSource = dataSource(node);
            targetDataSources.put(node, dataSource);
        });

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
//        dataSource.setDefaultTargetDataSource(test2DataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

}
