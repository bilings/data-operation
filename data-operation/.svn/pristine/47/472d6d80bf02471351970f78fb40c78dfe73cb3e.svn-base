package com.hifo.dataoperation.service.estate.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.dao.BusMergeRelDao;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.estate.BusMergeRelMapper;
import com.hifo.dataoperation.entity.BusMergeRel;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.service.estate.BusMergeRelService;
import com.hifo.dataoperation.base.MybatisPlusService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BusMergeRelServiceImpl implements BusMergeRelService {
    @Autowired
    private BusMergeRelDao busMergeRelDao;
    @Autowired
    private BusEstateDao busEstateDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void log(BusEstate from, BusEstate to, ExtEmployee emp) {
        // 删除fromId
        delete(from.getId());
        // 保存新的fromId -> toId
        BusMergeRel mergeLog = new BusMergeRel();
        mergeLog.setFromId(from.getId());
        mergeLog.setToId(to.getId());
        mergeLog.setCreateTime(new Date());
//        mergeLog.setCreateId(emp.getId().intValue());
//        mergeLog.setCreateName(emp.getName());
//        mergeLog.setOrganizationId(emp.getOrganizationId());
        busMergeRelDao.insert(mergeLog);

        // 获取合并到fromId的楼盘Id
        Map<String, Object> map = new HashMap<>();
        map.put("toId", from.getId());
        var list = busMergeRelDao.selectByCondition(map, null, null);
        // 依次做递归操作
        for (BusMergeRel log : list) {
            BusEstate busEstate = busEstateDao.selectById(log.getFromId(), BusEstate.class);
            log(busEstate,to,null);
        }
    }

    @Override
    public void delete(String fromId) {
        Map<String, Object> map = new HashMap<>();
        map.put("fromId", fromId);
        List<BusMergeRel> list = busMergeRelDao.selectByCondition(map, null, null);
        list.stream().forEach(e -> busMergeRelDao.deleteById(e.getId()));
    }

    @Override
    public List<BusMergeRel> listByToId(String toId) {
        Map<String, Object> map = new HashMap<>();
        map.put("toId", toId);
        List<BusMergeRel> list = busMergeRelDao.selectByCondition(map, null, null);
        return list;
    }
}
