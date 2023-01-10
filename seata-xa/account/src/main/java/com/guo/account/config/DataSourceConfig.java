package com.guo.account.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean("dataSourceProxy")
    @Primary
    public DataSource dataSourceProxy(DruidDataSource druidDataSource){
        return new DataSourceProxyXA(druidDataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSourceProxy) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSourceProxy);
        bean.setTransactionFactory(new SpringManagedTransactionFactory());
        return bean.getObject();
    }
}
