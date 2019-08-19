package com.zxz.freedomauth.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.zxz.freedomauth.dao.dameng", sqlSessionTemplateRef  = "damengSqlSessionTemplate")
public class DataSourceDaMengConfig {

    private final static Logger logger = LoggerFactory.getLogger(DataSourceDaMengConfig.class);
    @Bean(name = "damengDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dameng")
    @Primary
    public DataSource damengDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "damengSqlSessionFactory")
    @Primary /*此处必须在主数据库的数据源配置上加上@Primary*/
    public SqlSessionFactory damengSqlSessionFactory(@Qualifier("damengDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        /*加载mybatis全局配置文件*/
        //bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));
        /*加载所有的mapper.xml映射文件*/
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/gzpi/dameng/dao/dameng/**/*.xml"));
        //添加插件  （改为使用配置文件加载了）
        //bean.setPlugins(pageHelper());
        bean.setTypeAliasesPackage("com.zxz.freedomauth.pojo");
        logger.info("加载DM数据库连接......");
        return bean.getObject();
    }

    @Bean(name = "damengTransactionManager")
    @Primary
    public DataSourceTransactionManager damengTransactionManager(@Qualifier("damengDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "damengSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate damengSqlSessionTemplate(@Qualifier("damengSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
