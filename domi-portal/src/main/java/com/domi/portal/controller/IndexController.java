package com.domi.portal.controller;

import com.domi.portal.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Author 卡卡
 * Created by jing on 2016/12/18.
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");

        String indexAd11 = this.indexService.queryIndexAd1();
        mv.addObject("indexAD1", indexAd11);

        return mv;
    }
}
