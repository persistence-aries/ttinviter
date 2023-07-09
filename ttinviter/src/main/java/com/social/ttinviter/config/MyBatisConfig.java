package com.social.ttinviter.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Spring framework 就需要設定MyBatisConfig (Spring boot 也需要)
 * 需要將 MyBatisConfig 配置文件或者配置類的位置告诉 Spring
 * 新增MyBatis類後，需在myappApplicationg加註@Import(MyBatisConfig.class)
 * @author Aries Chang
 *
 */

@Configuration
public class MyBatisConfig {
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url("jdbc:mysql://mysqldb:3306/springbootmybatis") // 資料庫連接URL
                .username("root") //資料庫用戶名
                .password("passwd123") // 資料庫密碼
                .build();
    }
    // 創建SqlSessionFactory
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 設定Mapper文件路徑
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/mappers/*.xml"));
        return factoryBean.getObject();
    }
    // 配置事務管理器
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
