package com.hifo.dataoperation.service.setting.impl;

import com.hifo.dataoperation.mapper.setting.FieldMapper;
import com.hifo.dataoperation.entity.CategoryDictionary;
import com.hifo.dataoperation.service.setting.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张宗朋
 * @description
 * @create 8:51
 */
@Service
public class FieldServiceImpl implements FieldService {
    @Autowired
    private FieldMapper fieldMapper;

    @Override
    public Map<String, List<CategoryDictionary>> list() {
        List<CategoryDictionary> list = fieldMapper.listAllCategoryDictionary();
        Map<String, List<CategoryDictionary>> map = new HashMap<>();
        for (CategoryDictionary li : list) {
            //判断如果是bool类型则不用再页面展示
            if(!"bool".equals(li.getDescription())){
                if (map.containsKey(li.getDescription())) {
                    List<CategoryDictionary> listCD = map.get(li.getDescription());
                    listCD.add(li);
                    map.put(li.getDescription(), listCD);
                } else {
                    List<CategoryDictionary> listCD = new ArrayList<>();
                    listCD.add(li);
                    map.put(li.getDescription(), listCD);
                }
            }
        }
        return map;
    }

    @Override
    public void save(String value) {
        // fieldMapper.insert(categoryDictionary);
    }
}
