package com.domi.manager.service;

import com.domi.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Author 卡卡
 * Created by jing on 2016/12/14.
 */
@Service
public class PicService {

    @Value("${IMAGE_SERVICE_BASE_URL}")
    private String IMAGE_SERVICE_BASE_URL;

    public Map uploadFile(MultipartFile uploadFile){
        try {
            FastDFSClient client = new FastDFSClient("E:\\domi2.0\\domi-manager\\domi-manager-web\\src\\main\\resources\\resource\\client.conf");
            //获取图片原始名称
            String originalFilename = uploadFile.getOriginalFilename();
            //取扩展名
            String extendname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String url = client.uploadFile(originalFilename.getBytes(), extendname);
            //拼接url
            url = IMAGE_SERVICE_BASE_URL + url;
            //返回map数据
            Map result = new HashMap();
            result.put("error", 0);
            result.put("url", url);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            //返回map数据
            Map result = new HashMap();
            result.put("error", 1);
            result.put("message", "上传失败");

            return result;
        }
    }
}
