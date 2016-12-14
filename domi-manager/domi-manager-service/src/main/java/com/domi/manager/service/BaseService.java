package com.domi.manager.service;

import com.domi.manager.pojo.BasePojo;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Author 卡卡
 * Created by jing on 2016/12/12.
 */
public abstract class BaseService<T extends BasePojo> {

    @Autowired
    private Mapper<T> mapper;

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public T queryById(Long id){
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<T> queryAll(){
        return this.mapper.select(null);
    }

    /**
     * 根据条件查询一条数据
     * @param t
     * @return
     */
    public T queryOne(T t){
        return this.mapper.selectOne(t);
    }

    /**
     * 根据条件查询数据列表
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t){
        return this.mapper.select(t);
    }

    /**
     * 分页查询数据列表
     * @param page
     * @param rows
     * @param t
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page, Integer rows, T t){
        PageHelper.startPage(page,rows);
        List<T> list = this.mapper.select(t);
        return new PageInfo<T>(list);
    }

    /**
     * 新增数据
     * @param t
     * @return
     */
    public Integer save(T t){
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insert(t);
    }

    /**
     * 更新数据
     * @param t
     * @return
     */
    public Integer saveSelective(T t){
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(t);
    }

    /**
     * 有选择的更新，选择不为null的字段作为插入字段
     * @param t
     * @return
     */
    public Integer updateSelective(T t){
        t.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 根据id删除数据
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz, String property, List<Object> values){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property,values);
        return this.mapper.deleteByExample(example);
    }

    public Integer deleteByWhere(T t){
        return this.mapper.delete(t);
    }
}
