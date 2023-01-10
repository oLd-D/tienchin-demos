package com.guo.dd.datasource;

import java.util.Collection;

/**
 * 用于存储当前线程所使用的的数据源名称
 */
public class DynamicDataSourceContextHolder {
    private static ThreadLocal<String> CONTEXT_HOLDER=new ThreadLocal<>();

    public static void setDataSourceType(String dsType){
        CONTEXT_HOLDER.set(dsType);
    }

    public static String getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }
    public static void clearDataSourceType(){
        CONTEXT_HOLDER.remove();
    }
}
