package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SetmealMapper {

    /**
     * 根据菜品id判断是否有菜品被套餐绑定
     * @param ids
     * @return
     */
    Integer countMealDish(ArrayList<Long> ids);


    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
}
