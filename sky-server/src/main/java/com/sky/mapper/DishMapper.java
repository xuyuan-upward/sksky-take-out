package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     *  跟据id获取每个id菜品数据
     * @param id
     * @return
     */
    @Select("select *from dish where id=#{id}")
    Dish getById(Long id);

    /**
     *  根据id删除每个菜品数据
     * @param id
     */
    @Delete("delete from dish where id=#{id}")
    void deleteBatch(Long id);

    /**
     *  修改菜品信息
     */
    void Update(Dish dish);


    List<Dish> list(Dish dish);
}
