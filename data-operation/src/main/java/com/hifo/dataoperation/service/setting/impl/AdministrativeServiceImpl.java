package com.hifo.dataoperation.service.setting.impl;

import java.util.*;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hifo.dataoperation.dao.BusAdministrativeDao;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Table;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.BusAdministrative;
import com.hifo.dataoperation.mapper.setting.BusAdministrativeMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.AdministrativeService;
import com.hifo.dataoperation.util.StringUtil;

import lombok.var;

/**
 * 各个机构独立设置行政区划服务
 *
 * @author 杨捷
 * @Date 2019年4月23日
 */
@Service
@Scope("prototype")
public class AdministrativeServiceImpl implements AdministrativeService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private BusAdministrativeDao busAdministrativeDao;

//    /**
//     * 更新行政区划
//     *
//     * @param entity
//     * @return 是否成功
//     */
//    @Override
//    public boolean updateAdministrative(BusAdministrative entity) {
//        if (entity != null) {
//            QueryWrapper<BusAdministrative> updateWrapper = new QueryWrapper<BusAdministrative>();
//            updateWrapper.eq("mongoId", entity.getMongoId()).eq("organizationId", basicService.getLoginEmployee().getOrganizationId());
//            return this.update(entity, updateWrapper);
//        }
//
//        return false;
//    }

    /**
     * 按照城市筛选，返回该城市下的所有下属区县，街道
     *
     * @return
     */
    @Override
    public List<BusAdministrative> list(Pagination pages, BusAdministrative parmEntity) {
//        if (parmEntity != null && parmEntity.getId() != null) {
//            initParm(parmEntity);
////            Integer organizationId = basicService.getLoginEmployee().getOrganizationId();
//            QueryWrapper<BusAdministrative> query = new QueryWrapper<BusAdministrative>();
//            query.eq("mongoId", parmEntity.getMongoId()).eq("organizationId", organizationId);
//            BusAdministrative entity = baseMapper.selectOne(query);
//            if (entity != null) {
//                // 添加机构
//                parmEntity.setOrganizationId(organizationId);
//
//                String regionId = entity.getRegionId().replace("0", " ").trim().replace(" ", "0");
//                if (regionId.length() < 2) {
//                    regionId = regionId + "0";
//                }
//                parmEntity.setRegionId(regionId);
//                return baseMapper.query(pages, parmEntity);
//            }
//        }
        return null;
    }

    private void initParm(BusAdministrative parmEntity) {
        if (StringUtils.isBlank(parmEntity.getParentId())) {
            parmEntity.setParentId(null);
        }
        if (StringUtils.isBlank(parmEntity.getName())) {
            parmEntity.setName(null);
        }
    }


    @Override
    public List<BusAdministrative> listByParams(BusAdministrative entity) {
        Map<String, Object> query = new HashMap<>();
        if (StringUtil.isNotEmpty(entity.getType())) {
            query.put("type", entity.getType());
        }
        if (StringUtil.isNotEmpty(entity.getParentName())) {
            query.put("parentName", entity.getParentName());
        }
        if (entity.getParentId() != null) {
            query.put("parentId", entity.getParentId());
        }
        return busAdministrativeDao.selectByCondition(query, null, null);
    }

    @Override
    public Map<String, String> getMapByParentId(String parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("id", parentId);
//        var list = baseMapper.selectList(new QueryWrapper<BusAdministrative>().and(query -> new QueryWrapper<BusAdministrative>().eq("parentId", parentId)).or().eq("mongoId", parentId));
        var list = busAdministrativeDao.selectByCondition(params, null, null);
        Map<String, String> map = new HashMap<>(list.size());
        list.stream().forEach(e -> {
            map.put(e.getId(), e.getName());
        });
        return map;
    }

    @Override
    public List<Map> listByOpen(String key,String empId) {
        Map<String, Object> params = new HashMap<>();
        Pattern pattern = Pattern.compile("^.*"+key+".*$", Pattern.CASE_INSENSITIVE);
        params.put("name", pattern);
        params.put("type", "城市");
        String listCity ="";
        Map emp=basicService.load2Map(Table.ext_employee, new Field("id", empId));
        if(emp!=null){
            listCity  = ( String) emp.get("citys");
        }
        var list = busAdministrativeDao.selectByCondition(params, null, null);
        List<Map> cityList =new ArrayList<>();
        List<String> finalListCity =  JSONArray.parseArray(listCity, String.class);
        list.stream().forEach(e->{
            Map<String, Object> map = new HashMap<>();
            map.put("name",e.getName());
            map.put("value",e.getId());
            System.out.println(finalListCity+"");
            if(finalListCity!=null && finalListCity.size()>0){
                for (String o : finalListCity) {
                    if(e.getId().equals(o)){
                        map.put("selected",true);
                    }
                }
            }
            cityList.add(map);
        });
        return cityList;
    }

}
