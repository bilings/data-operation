package com.hifo.dataoperation.service.coe.impl;

import com.hifo.dataoperation.dao.BusFloorCoeDao;
import com.hifo.dataoperation.entity.coe.FloorCoefficient;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.FloorCoefficientService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@Log
@Service
public class FloorCoefficientImpl implements FloorCoefficientService {


    @Autowired
    private BusFloorCoeDao busFloorCoeDao;

    @Autowired
    private BasicService basicService;

    @Override
    public FloorCoefficient get(String configId) {
        return busFloorCoeDao.selectById(configId,FloorCoefficient.class);
    }

    @Override
    public FloorCoefficient selectByCid(String cid) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        criteria.and("cid").is(cid);
        query.addCriteria(criteria);
        List<FloorCoefficient> list=busFloorCoeDao.selectByQuery(query,FloorCoefficient.class);
        if (list!=null&&list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public List<FloorCoefficient> selectList(String configId) {
        return null;
    }

    @Override
    public void save(FloorCoefficient entity) {
        entity.setCreateUser(basicService.getLoginEmployee().getName());
        entity.setCreateTime(new Date());
        busFloorCoeDao.insert(entity);
    }

    @Override
    public void delete(String id) {
        busFloorCoeDao.deleteById(id);
    }
}
