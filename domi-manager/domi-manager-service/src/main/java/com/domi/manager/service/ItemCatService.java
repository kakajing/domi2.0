package com.domi.manager.service;

import com.domi.common.bean.ItemCatData;
import com.domi.common.bean.ItemCatResult;
import com.domi.manager.pojo.ItemCat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Author 卡卡
 * Created by jing on 2016/12/10.
 */
@Service
public class ItemCatService extends BaseService<ItemCat>{


//
//    @Autowired
//    private ItemCatMapper itemCatMapper;
//
//    /**
//     * 根据父节点id查询商品类目列表
//     * @param parentId
//     * @return
//     */
//    public List<ItemCat> queryItemCat(Long parentId) {
//        ItemCat itemCat = new ItemCat();
//        itemCat.setParentId(parentId);
//        return this.itemCatMapper.select(itemCat);
//    }
    private static final ObjectMapper MAPPER = new ObjectMapper();

//    @Autowired
//    private JedisClient jedisClient;

    @Value("${REDIS_KEY}")
    private String REDIS_KEY;
    @Value("${REDIS_EXPIRE}")
    private Integer REDIS_EXPIRE;

    public ItemCatResult queryAllToTree() {

        ItemCatResult result = new ItemCatResult();
//        try {
//            // 先从缓存中命中，如果命中的话返回，没有命中，程序继续执行
//            String json = jedisClient.get(REDIS_KEY);
//            if (StringUtils.isNotEmpty(json)){
//                // 命中
//                return JsonUtils.jsonToPojo(json, ItemCatResult.class);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : cats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())){
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        //封装一级对象
        List<ItemCat> itemCatList = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
            result.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()){
                continue;
            }

            //封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                itemCatData.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if (itemCat2.getIsParent()){
                    //封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<String>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }

            if (result.getItemCats().size() >= 14){
                break;
            }
        }

//        try {
//            // 将结果集写入到缓存中
//         //   jedisClient.hset(REDIS_KEY, MAPPER.writeValueAsString(result), REDIS_EXPIRE);
//            jedisClient.set(REDIS_KEY, MAPPER.writeValueAsString(result));
//            jedisClient.expire(REDIS_KEY, REDIS_EXPIRE);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        return result;
    }
}
