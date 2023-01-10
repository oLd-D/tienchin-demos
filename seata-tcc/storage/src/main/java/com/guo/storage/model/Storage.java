package com.guo.storage.model;


public class Storage {
    private Integer id;
    private String commodityCode;
    private Integer count;
    private Integer freezeCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFreezeCount() {
        return freezeCount;
    }

    public void setFreezeCount(Integer freezeCount) {
        this.freezeCount = freezeCount;
    }
}
