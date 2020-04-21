package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.entity.CategoryDictionary;
import com.hifo.dataoperation.service.setting.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 张宗朋
 * @description
 * @create 8:48
 */
@Controller
@RequestMapping("field")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    /**
     * 获取字典列表
     * @return
     */
    @RequestMapping("category_list")
    @ResponseBody
    public ApiResult list(){
        Map<String,List<CategoryDictionary>> map = fieldService.list();
        return ApiResult.success(map);
    }

    /**
     * 保存机构自己定义字典列表
     * params value
     * @return
     */
    @RequestMapping("category_save")
    @ResponseBody
    public ApiResult save(String value){
         fieldService.save(value);
        return ApiResult.success("");
    }
}
