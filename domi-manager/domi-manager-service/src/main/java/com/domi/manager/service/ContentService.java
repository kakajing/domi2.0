package com.domi.manager.service;

import com.domi.common.bean.EasyUIResult;
import com.domi.manager.mapper.ContentMapper;
import com.domi.manager.pojo.Content;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/22.
 */
@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;

    public EasyUIResult queryListByCategoryId(Long categoryId, Integer page, Integer rows) {

        PageHelper.startPage(page, rows);
        List<Content> contents = this.contentMapper.queryListByCategoryId(categoryId, page, rows);
        PageInfo<Content> pageInfo = new PageInfo<Content>(contents);

        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
    }
}
