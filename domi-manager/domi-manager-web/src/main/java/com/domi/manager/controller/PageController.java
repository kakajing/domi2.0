package com.domi.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 通用的页面跳转
 * Author 卡卡
 * Created by jing on 2016/12/10.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/page/{pageName}", method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
    }
}
