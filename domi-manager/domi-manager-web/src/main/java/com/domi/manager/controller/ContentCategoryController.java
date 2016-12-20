package com.domi.manager.controller;

import com.domi.manager.pojo.ContentCategory;
import com.domi.manager.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/20.
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentCategoryController.class);

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 根据父节点id查询子节点
     * @param parentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryListByParentId(
            @RequestParam(value = "id", defaultValue = "0") Long parentId){

        try {
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("根据父节点id查询子节点 parentId = {}", parentId);
            }

            ContentCategory record = new ContentCategory();
            record.setParentId(parentId);
            List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);

            if (list == null || list.isEmpty()){
                if (LOGGER.isInfoEnabled()){
                    LOGGER.info("根据父节点id查询子节点未找到 parentId = {}",parentId);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            LOGGER.error("根据父节点id查询子节点服务器失败了 parentId = {}", parentId, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategory){
//
//        try {
//            contentCategory.setId(null);
//            contentCategory.setIsParent(false);
//            contentCategory.setSortOrder(1);
//            contentCategory.setStatus(1);
//            this.contentCategoryService.save(contentCategory);
//
//            //判断父节点的isParent是否为true，不是，需要修改为true
//            ContentCategory parent = this.contentCategoryService.queryById(contentCategory.getParentId());
//            if (!parent.getIsParent()){
//                parent.setIsParent(true);
//                this.contentCategoryService.updateSelective(parent);
//            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }

    /**
     * 新增节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> insertContentCategory(ContentCategory contentCategory) {
        try {
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("新增节点 contentCategory = {}", contentCategory);
            }
            this.contentCategoryService.insertContentCategory(contentCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            LOGGER.error("新增节点服务器失败了 contentCategory = {}", contentCategory, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 重命名
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> renameContentCategory(ContentCategory contentCategory){

        try {
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("重命名 contentCategory = {}", contentCategory);
            }
            this.contentCategoryService.updateSelective(contentCategory);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("重命名服务器失败了 contentCategory = {}", contentCategory, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 删除节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(ContentCategory contentCategory){
        try {
            if (LOGGER.isInfoEnabled()){
                LOGGER.info("删除节点 contentCategory = {}", contentCategory);
            }
            this.contentCategoryService.delete(contentCategory);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            LOGGER.error("删除节点服务器失败了 contentCategory = {}", contentCategory, e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
