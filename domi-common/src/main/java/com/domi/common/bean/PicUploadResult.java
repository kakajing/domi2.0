package com.domi.common.bean;

/**
 * Author 卡卡
 * Created by jing on 2016/12/16.
 */
public class PicUploadResult {

    //上传是否成功的判断标识，0-成功，1-失败
    private Integer error;

    private String url;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getError() {
        return error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
