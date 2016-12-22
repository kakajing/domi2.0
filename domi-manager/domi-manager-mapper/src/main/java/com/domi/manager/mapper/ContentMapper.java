package com.domi.manager.mapper;

import com.domi.manager.pojo.Content;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper extends Mapper<Content>{

    List<Content> queryList(Long categoryId);

    List<Content> queryListByCategoryId(
            @Param("categoryId") Long categoryId,
            @Param("page") Integer page, @Param("rows") Integer rows);

}
