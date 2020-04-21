package com.hifo.dataoperation.service.setting;

import com.hifo.dataoperation.entity.CategoryDictionary;

import java.util.List;
import java.util.Map;

public interface FieldService {
    Map<String,List<CategoryDictionary>> list();

    void save(String value);
}
