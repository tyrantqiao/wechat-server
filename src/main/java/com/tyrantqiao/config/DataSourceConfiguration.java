package com.tyrantqiao.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * @author tyrantqiao
 * date: 2018/11/15
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@Configuration
@MapperScan("com.tyrantqiao.dao")
public class DataSourceConfiguration {
    @Value("${db.dataSourceUrl}")
    private static String dataSourceUrl;
    @Value("${db.username}")
    private static String username;
    @Value("${db.password}")
    private static String password;

    public static DataSource getDataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriver("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
