package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     *  获取每个id对应的套餐数目
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
