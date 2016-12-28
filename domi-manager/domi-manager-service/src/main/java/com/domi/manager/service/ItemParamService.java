package com.domi.manager.service;

import com.domi.common.bean.EasyUIResult;
import com.domi.manager.mapper.ItemParamexdMapper;
import com.domi.manager.pojo.ItemParam;
import com.domi.manager.pojo.ItemParamexd;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/17.
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamexdMapper itemParamexdMapper;

    public EasyUIResult getItemParamList(int page, int rows){
        //分页处理
        PageHelper.startPage(page, rows);

        //执行查询
        List<ItemParamexd> list = itemParamexdMapper.selectList();
        //取分页信息
        PageInfo pageInfo = new PageInfo<>(list);
        //返回处理结果
        EasyUIResult result = new EasyUIResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

}
