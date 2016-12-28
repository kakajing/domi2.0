package com.domi.portal.service;

import com.domi.common.utils.HttpClientUtil;
import com.domi.common.utils.JsonUtils;
import com.domi.manager.pojo.ItemDesc;
import com.domi.portal.bean.Item;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Author 卡卡
 * Created by jing on 2016/12/25.
 */
@Service
public class ItemService {

    @Value("${MANAGER_DOMI_URL}")
    private String MANAGER_DOMI_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 商品商品基本数据
     * @param itemId
     * @return
     */
    public Item queryItemById(Long itemId) {

        try {
            String url = MANAGER_DOMI_URL + "/item/" + itemId;
            String jsonData = HttpClientUtil.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
         //   return MAPPER.readValue(jsonData, Item.class);
            return JsonUtils.jsonToPojo(jsonData, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 显示商品描述
     * @param itemId
     * @return
     */
    public ItemDesc queryItemDescByItemId(Long itemId) {
        try {
            String url = MANAGER_DOMI_URL + "/item/desc/" + itemId;
            String jsonData = HttpClientUtil.doGet(url);
            if (StringUtils.isEmpty(jsonData)){
                return null;
            }
            return JsonUtils.jsonToPojo(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 显示商品规格参数
     * @param itemId
     * @return
     */
    public String queryItemParamByItemId(Long itemId) {

        try {
            String url = MANAGER_DOMI_URL + "/item/param/item/" + itemId;
            String jsonData = HttpClientUtil.doGet(url);
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            String paramData = jsonNode.get("paramData").asText();
            JsonNode arrayNode = MAPPER.readTree(paramData);

            StringBuffer sb = new StringBuffer();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
            for (JsonNode node : arrayNode) {
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + node.get("group").asText()
                        + "</th></tr>");
                ArrayNode params = (ArrayNode) node.get("params");
                for (JsonNode param : params) {
                    sb.append("<tr><td class=\"tdTitle\">" + param.get("k").asText() + "</td><td>"
                            + param.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
