package com.domi.manager.service;

import com.domi.manager.mapper.ItemParamItemMapper;
import com.domi.manager.pojo.ItemParamItem;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author 卡卡
 * Created by jing on 2016/12/17.
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {
    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    /**
     * 更新规格参数数据
     * @param itemid
     * @param itemParams
     */
    public void updateItemParamItem(Long itemid, String itemParams) {

        //更新的对象
        ItemParamItem record = new ItemParamItem();
        record.setItemId(itemid);
        record.setParamData(itemParams);
        record.setUpdated(new Date());

        //更新条件
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", itemid);
        this.itemParamItemMapper.updateByExampleSelective(record, example);
    }
}
