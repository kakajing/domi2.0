package com.domi.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/18.
 */
public class ItemCatData {

    @JsonProperty("u")  // 序列化成json数据时为 u
    private String url;

    @JsonProperty("n")
    private String name;

    @JsonProperty("i")
    private List<?> items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}