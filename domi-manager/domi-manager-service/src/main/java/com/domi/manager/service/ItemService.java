package com.domi.manager.service;

import com.domi.common.utils.IDUtils;
import com.domi.manager.mapper.ItemMapper;
import com.domi.manager.pojo.Item;
import com.domi.manager.pojo.ItemDesc;
import com.domi.manager.pojo.ItemParamItem;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/12.
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemParamItemService itemParamItemService;

    @Autowired
    private ItemMapper itemMapper;

    public void saveItem(Item item, String desc, String itemParams) {
        long itemId = IDUtils.genItemId();
        item.setId(itemId);

        // 设置初始数据
        item.setStatus(1);
      //  item.setId(null);// 强制设置id为null

        super.save(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        // 保存描述数据
        this.itemDescService.save(itemDesc);

        //保存规格参数数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        this.itemParamItemService.save(itemParamItem);

    }

    public PageInfo<Item> queryPageList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");

        //设置分页参数
        PageHelper.startPage(page, rows);

        List<Item> items = this.itemMapper.selectByExample(example);
        return new PageInfo<Item>(items);
    }

    public void updateItem(Item item, String desc, String itemParams) {
       //强制设置不能修改的字段为null
        item.setStatus(null);
        item.setCreated(null);
        super.updateSelective(item);

        //修改商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateSelective(itemDesc);

        this.itemParamItemService.updateItemParamItem(item.getId(), itemParams);
    }
}
