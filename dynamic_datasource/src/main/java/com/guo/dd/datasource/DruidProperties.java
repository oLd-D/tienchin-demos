package com.guo.dd.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;


/**
 * 跟yaml文件对应起来，名字需要一致
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {
    private String type;
    private String driverClassName;
    private Map<String, Map<String,String>> ds;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;

    // 原本只自定义数据源没有设置后面的属性，需要默认设置一下
    public DataSource dataSource(DruidDataSource druidDataSource){
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        return druidDataSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Map<String, Map<String, String>> getDs() {
        return ds;
    }

    public void setDs(Map<String, Map<String, String>> ds) {
        this.ds = ds;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }
}
