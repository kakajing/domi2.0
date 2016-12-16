package com.domi.manager.controller;

import com.domi.manager.pojo.ItemDesc;
import com.domi.manager.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Author 卡卡
 * Created by jing on 2016/12/17.
 */
@Controller
@RequestMapping("/item/desc")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 根据商品id查询商品描述
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId){
        try {
            ItemDesc itemDesc = this.itemDescService.queryById(itemId);
            if (itemDesc == null){
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //200
            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
