package com.domi.manager.service;

import com.domi.common.bean.PicUploadResult;
import com.domi.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author 卡卡
 * Created by jing on 2016/12/14.
 */
@Service
public class PicService {

    @Value("${IMAGE_SERVICE_BASE_URL}")
    private String IMAGE_SERVICE_BASE_URL;

    public PicUploadResult uploadFile(MultipartFile uploadFile){

        PicUploadResult result = new PicUploadResult();
        //判断图片是否为空
        if ((uploadFile.isEmpty())){
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }

        try {
            //获取图片原始名称
            String originalFilename = uploadFile.getOriginalFilename();
            //取扩展名
            String extendname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            FastDFSClient client = new FastDFSClient("E:\\domi2.0\\domi-manager\\domi-manager-web\\src\\main\\resources\\resource\\client.conf");

            String url = client.uploadFile(uploadFile.getBytes(), extendname);
            //拼接url
            url = IMAGE_SERVICE_BASE_URL + url;

            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
          result.setError(1);
            result.setMessage("图片上传失败");
        }

        return result;
    }
}
