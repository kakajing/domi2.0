package com.domi.portal.controller;

import com.domi.manager.pojo.ItemDesc;
import com.domi.portal.bean.Item;
import com.domi.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author 卡卡
 * Created by jing on 2016/12/25.
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("item");
        //基本数据
        Item item = this.itemService.queryItemById(itemId);
        mv.addObject("item", item);

        //描述数据
        ItemDesc itemDesc = this.itemService.queryItemDescByItemId(itemId);
        mv.addObject("itemDesc", itemDesc);

        //商品规格参数
        String html = this.itemService.queryItemParamByItemId(itemId);
        mv.addObject("itemParam", html);
        return mv;
    }
}
