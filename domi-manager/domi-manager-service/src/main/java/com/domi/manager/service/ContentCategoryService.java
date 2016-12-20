package com.domi.manager.service;

import com.domi.manager.mapper.ContentCategoryMapper;
import com.domi.manager.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/20.
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

   public void insertContentCategory(ContentCategory contentCategory){
       contentCategory.setIsParent(false);

       //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数'
       contentCategory.setSortOrder(1);
       //1(正常),2(删除)
       contentCategory.setStatus(1);
       contentCategory.setCreated(new Date());
       contentCategory.setUpdated(new Date());

       contentCategoryMapper.insert(contentCategory);

       Long id = contentCategory.getId();
       ContentCategory parent = contentCategoryMapper.selectByPrimaryKey(id);
       //判断父节点的isParent是否为true，不是，需要修改为true
       if (!parent.getIsParent()){
           parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
       }
   }

    public void delete(ContentCategory contentCategory){

        //查找所有的子节点
        List<Object> ids = new ArrayList<Object>();
        ids.add(contentCategory.getId());

        //调用递归方法添加旗下所有子节点id
        findAllNode(contentCategory.getId(), ids);

        //删除所以的子节点
        super.deleteByIds(ContentCategory.class, "id", ids);

        // 判断当前节点的父节点是否还有其他的子节点，如果没有，修改isParent为false
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = super.queryListByWhere(record);
        if (list == null || list.isEmpty()) {
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            super.updateSelective(parent);
        }
    }

   private void findAllNode(Long parentId, List<Object> ids){
       ContentCategory record = new ContentCategory();
       record.setParentId(parentId);
       List<ContentCategory> list = super.queryListByWhere(record);
       for (ContentCategory contentCategory : list) {
           ids.add(contentCategory.getId());
           // 判断该节点是否为父节点，如果是，进行递归
           if (contentCategory.getIsParent()){
               findAllNode(contentCategory.getId(), ids);
           }
       }
   }
}
