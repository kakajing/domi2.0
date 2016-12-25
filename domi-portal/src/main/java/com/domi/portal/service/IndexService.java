package com.domi.portal.service;

import com.domi.common.utils.HttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author 卡卡
 * Created by jing on 2016/12/23.
 */
@Service
public class IndexService {

    @Value("${MANAGER_DOMI_URL}")
    private String MANAGER_DOMI_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

//    @Value("${INDEX_AD2_URL}")
//    private String INDEX_AD2_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @SuppressWarnings(("unchecked"))
    public String queryIndexAd1(){

        try {
            String url = MANAGER_DOMI_URL + INDEX_AD1_URL;
            String jsonData = HttpClientUtil.doGet(url);
            if (jsonData == null){
                return null;
            }

            //解析json数据
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("srcB", row.get("pic").asText());
                map.put("height", 240);
                map.put("alt", row.get("title").asText());
                map.put("width", 670);
                map.put("src", row.get("pic").asText());
                map.put("widthB", 550);
                map.put("href", row.get("url").asText());
                map.put("heightB", 240);

                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
