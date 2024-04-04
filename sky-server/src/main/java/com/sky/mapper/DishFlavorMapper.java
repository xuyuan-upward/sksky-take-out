package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据id删除口味
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteById(long id);

    /**
     * 根据id查询口味
     * @param id
     */
    @Select("select *from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getByDishId(Long id);
}
