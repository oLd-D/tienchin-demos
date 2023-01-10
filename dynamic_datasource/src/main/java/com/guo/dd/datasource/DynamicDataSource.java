package com.guo.dd.datasource;

import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// 最开始的时候统一把所有数据源加载进来,会去寻找实现了AbstractRoutingDataSource的类
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    // 创建对象的时候就把所有可以使用的数据源加载进来
    public DynamicDataSource(LoadDataSource loadDataSource){
        // 通过 loadDataSource 类拿到所有的数据源
        Map<String, DataSourceProxy> allDs = loadDataSource.loadAllDataSource();
        // 可以使用哪些数据源
        super.setTargetDataSources(new HashMap<>(allDs));
        // 默认使用哪些数据源（没加@DataSource就用的这个数据源）
        super.setDefaultTargetDataSource(allDs.get(DataSourceType.DEFAULT_DS_NAME));
        // 初始化Bean对象要进行的操作
        super.afterPropertiesSet();
    }

    // 自动调用该方法，拿到当前线程经过切面类拦截之后存在ThreadLocal中的数据库名称
    // 即获取需要使用的数据库的字符串，方便通过字符串查找特定的数据源
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
