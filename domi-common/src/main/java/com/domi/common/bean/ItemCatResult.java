package com.domi.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台系统开发接口
 * Author 卡卡
 * Created by jing on 2016/12/18.
 */
public class ItemCatResult {

    @JsonProperty("data")  //指定序列化json后的名称
    private List<ItemCatData> itemCats = new ArrayList<ItemCatData>();

    public List<ItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }
}
