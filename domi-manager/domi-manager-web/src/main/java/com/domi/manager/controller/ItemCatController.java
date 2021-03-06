package com.domi.manager.controller;

import com.domi.manager.pojo.ItemCat;
import com.domi.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/10.
 */
@RequestMapping("item/cat")
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据父节点id查询商品类目表
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCat(
            @RequestParam(value = "id", defaultValue = "0") Long parentId){
        try {
        //    List<ItemCat> itemCats = this.itemCatService.queryItemCat(parentId);
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            List<ItemCat> itemCats = itemCatService.queryListByWhere(itemCat);
            if (itemCats == null && itemCats.isEmpty()){
                //资源不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
           return ResponseEntity.ok(itemCats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
