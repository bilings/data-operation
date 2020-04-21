package com.hifo.dataoperation.service.setting;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.BusAdministrative;
import com.hifo.dataoperation.pagination.Pagination;

/**
 * 各个机构独立设置行政区划服务
 *
 * @author 杨捷
 * @Date 2019年4月23日
 */
public interface AdministrativeService {
//    /**
//     * 更新行政区划
//     *
//     * @param entity
//     * @return 是否成功
//     */
//    boolean updateAdministrative(BusAdministrative entity);

    /**
     * 按照城市筛选，返回该城市下的所有下属区县，街道
     *
     * @param pages
     * @param entity
     * @return
     */
    List<BusAdministrative> list(Pagination pages, BusAdministrative entity);

    /**
     * 根据查询条件查询list
     *
     * @param entity
     * @return
     */
    List<BusAdministrative> listByParams(BusAdministrative entity);

    /**
     * 根据父级id查询子级行政区map
     *
     * @param parentId
     * @return
     */
    Map<String, String> getMapByParentId(String parentId);

    /**
     * 获取员工要开通的城市
     * @param key
     * @param empId 员工ID
     * @return
     */
    List<Map> listByOpen(String key,String empId);
}
