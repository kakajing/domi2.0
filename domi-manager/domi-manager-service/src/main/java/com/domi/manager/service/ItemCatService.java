package com.domi.manager.service;

import com.domi.manager.mapper.ItemCatMapper;
import com.domi.manager.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/10.
 */
@Service
public class ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    public List<ItemCat> queryItemCat(Long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return this.itemCatMapper.select(itemCat);
    }
}
