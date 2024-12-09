package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface FlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatchFlavors(List<DishFlavor> flavors);

    /**
     * 批量删除口味
     * @param flavors
     */
    void deleteBatchFlavors(List<DishFlavor> flavors);

    /**
     * 根据菜品id获取菜品口味
     * @param id
     * @return
     */
    ArrayList<DishFlavor> getFlavorById(Long id);
}
