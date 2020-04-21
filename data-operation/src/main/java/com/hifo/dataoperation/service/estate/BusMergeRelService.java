package com.hifo.dataoperation.service.estate;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.BusMergeRel;
import com.hifo.dataoperation.entity.ExtEmployee;

import java.util.List;

/**
 * 合并日志Service
 *
 * @author xmw
 * @date 2019/4/28 17:56
 */
public interface BusMergeRelService {

    /**
     * 记录合并日志，合并的时候需要将曾经合并到fromId的楼盘，设置为合并到toId
     *
     * @param from 源楼盘
     * @param to   目标楼盘
     * @param emp
     */
    void log(BusEstate from, BusEstate to, ExtEmployee emp);

    /**
     * 删除合并关系
     *
     * @param fromId 被合并楼盘
     */
    void delete(String fromId);

    /**
     * 获取合并到该楼盘的记录
     *
     * @param toId 合并到楼盘的id
     * @return List
     */
    List<BusMergeRel> listByToId(String toId);
}
