package com.hifo.dataoperation.service.estate.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hifo.dataoperation.dao.BusVirtualRelDao;

import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.entity.BusVirtualRel;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusVirtualRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合并日志Service
 *
 * @author whc
 * @date 2019/3/27 14:56
 */
@Service
public class BusVirtualRelServiceImpl implements BusVirtualRelService {

    @Autowired
    private BasicService basicService;

    @Autowired
    private BusVirtualRelDao busVirtualRelDao;

    @Override
    public void log(String fromId, String toId) {
        // 删除fromId
        delete(fromId);
        // 保存新的fromId -> toId
        BusVirtualRel busVirtualRel = new BusVirtualRel();
        busVirtualRel.setFromId(fromId);
        busVirtualRel.setToId(toId);
//        busVirtualRel.setCreateName(basicService.getLoginEmployee().getName());
        busVirtualRel.setCreateTime(new Date());
        busVirtualRelDao.insert(busVirtualRel);

        Query query = new Query();
        Criteria criteria = Criteria.where("toId").is(fromId);
        query.addCriteria(criteria);
        List<BusVirtualRel> list = busVirtualRelDao.selectByQuery(query, BusVirtualRel.class);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BusVirtualRel busVirtualRel1 : list) {
                log(busVirtualRel1.getFromId(), toId);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("toId", fromId);
        busVirtualRelDao.deleteByCondition(map);
    }

    @Override
    public void delete(String fromId) {
        Map<String, Object> map = new HashMap<>();
        map.put("fromId", fromId);
        busVirtualRelDao.deleteByCondition(map);
    }

    @Override
    public List<BusVirtualRel> listByFromId(String fromId) {
        Map<String, Object> params = new HashMap<>();
        params.put("fromId", fromId);
        return busVirtualRelDao.selectByCondition(params, null, null);

    }

    @Override
    public List<BusVirtualRel> listByToId(String toId) {
        Map<String, Object> params = new HashMap<>();
        params.put("toId", toId);
        return busVirtualRelDao.selectByCondition(params, null, null);
    }

    @Override
    public void update(BusVirtualRel busVirtualRel) {
        busVirtualRelDao.updateById(busVirtualRel.getId(), busVirtualRel);
    }
}
