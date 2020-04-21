package com.hifo.dataoperation.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.CategoryDictionary;

import java.util.List;

/**
 *@description  
 *@author 张宗朋
 *@create  9:01
 */
public interface FieldMapper extends BaseMapper<CategoryDictionary> {
         List<CategoryDictionary> listAllCategoryDictionary();
    }
