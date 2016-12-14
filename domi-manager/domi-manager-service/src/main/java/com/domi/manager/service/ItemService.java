package com.domi.manager.service;

import com.domi.manager.pojo.Item;
import com.domi.manager.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author 卡卡
 * Created by jing on 2016/12/12.
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    public void saveItem(Item item, String desc){
        //设置初始数据
        item.setStatus(1);
        item.setId(null); //强制设置id为null

        super.save(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);

        //保存描述数据
        this.itemDescService.save(itemDesc);


    }
}
