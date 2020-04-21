package com.hifo.dataoperation.service.crm.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusAdministrativeDao;
import com.hifo.dataoperation.entity.BusAdministrative;
import com.hifo.dataoperation.mapper.crm.ExtEmployeeMapper;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.crm.EmployeeService;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.service.setting.BusOrganizationCityService;
import com.hifo.dataoperation.util.Md5Util;
import com.hifo.dataoperation.util.StringUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 维护机构员工
 *
 * @author whc
 * @date 2019/3/13 14:55
 */
@Service
public class EmployeeServiceImpl extends MybatisPlusService<ExtEmployeeMapper, ExtEmployee> implements EmployeeService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private BusOrganizationCityService busOrganizationCityService;
    @Autowired
    private BusAdministrativeDao busAdministrativeDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ExtEmployee getEmployee(String username, String password) {
        List<ExtEmployee> list = baseMapper.selectList(new QueryWrapper<ExtEmployee>().eq("username", username));
        if (list.size() != 1) {
            return null;
        }
        ExtEmployee extEmployee = list.get(0);
        if (Md5Util.verify(password, extEmployee.getPassword())) {
            return extEmployee;
        } else {
            return null;
        }
    }

    @Override
    public IPage getEmployeeList(int organizationId, String keyword, Pagination pages) {
        QueryWrapper<ExtEmployee> query = new QueryWrapper();
        query.eq("organizationId", organizationId);
        if (StringUtil.isNotEmpty(keyword)) {
            query.and(wrapper -> wrapper.like("name", keyword).or().like("username", keyword));
        }
        var page = baseMapper.selectPage(pages, query);
        return page;
    }

    @Override
    public List<Document> getCityListByOrganization(String id) {
        List<Document> list = new ArrayList<>();
        String listCity ="";
        Map emp=basicService.load2Map(Table.ext_employee, new Field("id", id));
        if(emp!=null){
            listCity  = ( String) emp.get("citys");
        }
        DBObject queryCondition = new BasicDBObject();
        //age in [13, 47]
        BasicDBList values = new BasicDBList();
        if(StringUtils.isBlank(listCity)){
            return null;
        }
        List<String> finalListCity =  JSONArray.parseArray(listCity, String.class);
        for (String s :finalListCity) {
            System.out.println(s);
            values.add(s);
        }
        queryCondition.put("_id", new BasicDBObject("$in", values));
        FindIterable<Document> documents = mongoTemplate.getCollection("administrativeDivision").find((Bson) queryCondition);
        for (Document document : documents) {
            list.add(document);
        }
        return list;
    }

    @Override
    public Map findById(String id) {
        return basicService.load2Map(Table.ext_employee, new Field("id", id));
    }

    @Override
    public void update(ExtEmployee extEmployee) {
        // 把权限保存为json数组格式
        String moduleCodes = extEmployee.getModuleCodes();
        StringTokenizer st = new StringTokenizer(moduleCodes, ",");
        List<String> moduleList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            moduleList.add(st.nextToken());
        }
        // 更新数据库
        if (StringUtil.isNull(extEmployee.getId())) {
            // 密码md5加密
            extEmployee.setPassword(Md5Util.md5(extEmployee.getPassword()));
            // 新增
            basicService.update(Table.ext_employee, extEmployee);
        } else {
            // 不要更新密码
            extEmployee.setPassword(null);
            // 更新
            basicService.update(Table.ext_employee, extEmployee);
        }
    }

    @Override
    public void delete(String id) {
        basicService.delete(Table.ext_employee, Field.pk(id));
    }

    @Override
    public ApiResult updateCityById(Long id) {
        return ApiResult.success(busOrganizationCityService.updateCityById(id));
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult resetPassword(String id, String password, String pwd) {
        var obj = getById(id);
        if (obj == null) {
            return ApiResult.failed("当前用户不存在。");
        }
        if (StringUtil.isEmpty(pwd) || !obj.getPassword().equals(Md5Util.md5(pwd))) {
            return ApiResult.failed("原密码不正确。");
        }
        if (StringUtil.isEmpty(password)) {
            return ApiResult.failed("输入的密码不符合规范。");
        }
        if (obj.getPassword().equals(Md5Util.md5(password.trim()))) {
            return ApiResult.failed("新密码与原始密码一致。");
        }
        obj.setPassword(Md5Util.md5(password.trim()));
        updateById(obj);
        return ApiResult.success();
    }

}
