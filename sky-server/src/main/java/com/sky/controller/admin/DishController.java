package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Target;
import java.util.List;

/**
 *  菜品管理
 */
@RestController
@Slf4j
@Api(tags = "菜品管理")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分类查询：{}",dishPageQueryDTO);
         PageResult pageResult=dishService.page(dishPageQueryDTO);
         return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     * @param
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品:{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     *  根据id回显菜品
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品：{}",id);
        // 返回dishvo
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    /**
     *  修改菜品
     */
    @PutMapping
    public Result Update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}",dishDTO);
        log.info("菜品的id:{}",dishDTO.getId());
        dishService.UpdateWithFlavor(dishDTO);
        return Result.success();
    }
}
