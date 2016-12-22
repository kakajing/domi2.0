package com.domi.common.httpclient;

/**
 * Author 卡卡
 * Created by jing on 2016/12/23.
 */
public class HttpResult {

    private Integer code;
    private String data;



    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
