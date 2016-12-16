package com.domi.manager.controller;

import com.domi.common.bean.PicUploadResult;
import com.domi.common.utils.JsonUtils;
import com.domi.manager.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author 卡卡
 * Created by jing on 2016/12/14.
 */
@Controller
public class PicUploadController {
    @Autowired
    private PicService picService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        PicUploadResult result = picService.uploadFile(uploadFile);
        //需要把java对象手工转换成json数据
        String json = JsonUtils.objectToJson(result);
        return json;
    }
}
