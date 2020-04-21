package com.hifo.dataoperation.service.coe.impl;


import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusCoeDao;
import com.hifo.dataoperation.entity.BusAdministrative;
import com.hifo.dataoperation.entity.coe.FloorCityConfig;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.FloorCityConfigService;
import com.hifo.dataoperation.util.StringUtil;
import com.hifo.dataoperation.vo.FloorCityConfigVO;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import lombok.extern.java.Log;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;

import org.bson.Document;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author bxl
 * @create 18:21
 */
@Log
@Service
public class FloorCityConfigImpl implements FloorCityConfigService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private BusCoeDao busCoeDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public FloorCityConfig findById(String id) {
        return busCoeDao.selectById(id,FloorCityConfig.class);

    }

    @Override
    public FloorCityConfig findByQuery(Query query) {
        List<FloorCityConfig> list=busCoeDao.selectByQuery(query,FloorCityConfig.class);
        if (list!=null&&list.size()>0) return list.get(0);
       return null;
    }

    @Override
    public void delete(String[] ids) {
        for (String id:ids) {
            busCoeDao.deleteById(id);
            ///删除系数表数据 根据pid
        }
    }

    @Override
    public List<FloorCityConfig> List() {
        List<FloorCityConfig> list=busCoeDao.selectList(FloorCityConfig.class,null,null );
        Collections.sort(list, new Comparator<FloorCityConfig>() {
            public int compare(FloorCityConfig arg0, FloorCityConfig arg1) {
                return arg0.getType().compareTo(arg1.getType());
            }
        });
        return  list;
    }

    @Override
    public void save(FloorCityConfig entity) {

        busCoeDao.insert(entity);
    }

    @Override
    public void addConfig(FloorCityConfigVO configVO) {
        String loginUser=basicService.getLoginEmployee().getName();

        FloorCityConfig cityObject=null;
        FloorCityConfig strativeObject=null;
        FloorCityConfig areaObject=null;

        BasicDBList values = new BasicDBList();
        if (!StringUtil.isNull(configVO.getCityId())) values.add(configVO.getCityId());
        if (!StringUtil.isNull(configVO.getAdministrativeId())) values.add(configVO.getAdministrativeId());
        if (!StringUtil.isNull(configVO.getAreaId())) values.add(configVO.getAreaId());

        DBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new BasicDBObject("$in", values));
        FindIterable<Document> citys = mongoTemplate.getCollection("administrativeDivision").find((Bson) queryCondition);

        if (!StringUtil.isNull(configVO.getCityId())){
            cityObject=findByQuery(new Query().addCriteria(new Criteria().and("cityId").is(configVO.getCityId())));
            if (cityObject==null){
                cityObject=new FloorCityConfig();
                Document c=citys.filter(new Document("_id",configVO.getCityId())).first();
                if (c!=null){
                    cityObject.setCityId(c.getString("_id"));
                    cityObject.setCityName(c.getString("name"));
                }
                cityObject.setCreateTime(new Date());
                cityObject.setCreateUser(loginUser);
                cityObject.setType(configVO.getCoeTypeSelect());
                save(cityObject);
            }
        }
        if (!StringUtil.isNull(configVO.getAdministrativeId())){
            strativeObject=findByQuery(new Query().addCriteria(new Criteria().and("cityId").is(configVO.getAdministrativeId())));
            if (strativeObject==null){
                strativeObject=new FloorCityConfig();
                Document c=citys.filter(new Document("_id",configVO.getAdministrativeId())).first();
                if (c!=null){
                    strativeObject.setCityId(c.getString("_id"));
                    strativeObject.setCityName(c.getString("name"));
                }
                strativeObject.setCreateTime(new Date());
                strativeObject.setCreateUser(loginUser);
                strativeObject.setType(configVO.getCoeTypeSelect());
                strativeObject.setPid(cityObject.getId());
                save(strativeObject);
            }
        }

        if (!StringUtil.isNull(configVO.getAreaId())){
            areaObject=findByQuery(new Query().addCriteria(new Criteria().and("cityId").is(configVO.getAreaId())));
            if (areaObject==null){
                areaObject=new FloorCityConfig();
                Document c=citys.filter(new Document("_id",configVO.getAreaId())).first();
                if (c!=null){
                    areaObject.setCityId(c.getString("_id"));
                    areaObject.setCityName(c.getString("name"));
                }
                areaObject.setCreateTime(new Date());
                areaObject.setCreateUser(loginUser);
                areaObject.setType(configVO.getCoeTypeSelect());
                areaObject.setPid(strativeObject.getId());
                save(areaObject);
            }
        }


    }
}
