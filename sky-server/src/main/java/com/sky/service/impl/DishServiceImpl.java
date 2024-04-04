package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     *  添加菜品
     * @param dishDTO
     */
    @Transactional //开启事务
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        //向菜品表中加入一条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        //向口味中插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null || flavors.size() == 0) { //口味不是必须的
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(flavors); //批量加入
        }

    }

    /**
     * 菜品分类查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        //   PageHelper.startPage已经实现了limit的动态展现
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除菜品
     *
     * @param
     * @return
     */
    @Override
    public void deleteBatch(List<Long> ids) {

        //判断菜品是否能删除  ---》还在起售
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                // 当前菜品还在起售，不能被删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断菜品是否能删除  ---》菜品与套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() != 0) {
            // 当前菜品和某个套餐关联了不能被删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //可以删除，删除菜品的口味
        for (Long id : ids) {
            //删除菜品的口味
            dishFlavorMapper.deleteById(id);
            //删除菜品的数据
            dishMapper.deleteBatch(id);
        }

    }

    /**
     * 根据id回显菜品.和口味数据
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {

        //根据id查询菜品信息
        Dish dish = dishMapper.getById(id);

        //根据id查询口味信息
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getByDishId(id);

        // 将查询到的数据信息封装到DishVO里面
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 修改菜品基本信息和口味信息
     */
    @Override
    public void UpdateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 修改菜品的信息
        dishMapper.Update(dish);

        // 1.修改口味的信息
        //1.1删除该口味的信息
        Long dishId = dish.getId();
        dishFlavorMapper.deleteById(dishId);
        //1.2再保存当前的口味信息
        //向口味中插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null || flavors.size() == 0) { //口味不是必须的
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(flavors); //批量加入
        }
    }
    @Override
    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
