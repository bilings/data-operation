package com.hifo.dataoperation.service.estate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.estate.BusEstateAgentMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusEstateAgentService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.util.StringUtil;
import com.hifo.dataoperation.vo.BusEstateAgentVO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 合并推荐业务实现类
 *
 * @Author: xmw
 * @Date: 2019/5/24 15:10
 */
@Service
public class BusEstateAgentServiceImpl extends MybatisPlusService<BusEstateAgentMapper, BusEstateAgentVO> implements BusEstateAgentService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private BusEstateService busEstateService;

    @Override
    public IPage findByPage(BusEstateAgentVO vo, Pagination page) {
        if (StringUtil.isNotNull(vo.getFromName())) {
            vo.setFromName(new StringBuffer("%").append(vo.getFromName()).append("%").toString());
        }
        vo.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        page.setRecords(baseMapper.selectAgentList(page, vo));
        ((List<BusEstateAgentVO>) page.getRecords()).stream().forEach(e -> {
            if (StringUtil.isNotEmpty(e.getFromAddr())) {
                try {
                    e.setFromAddr(StringUtil.formatAddrJson2String(e.getFromAddr()));
                } catch (Exception ex) {
                    e.setFromAddr("");
                }
            }
            if (StringUtil.isNotEmpty(e.getToAddr())) {
                try {
                    e.setToAddr(StringUtil.formatAddrJson2String(e.getToAddr()));
                } catch (Exception ex) {
                    e.setToAddr("");
                }
            }
        });
        return page;
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult merges(String[] ids) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var fts = ids[i].split("_");
            busEstateService.merge(new String[]{fts[0]}, fts[1]);
        }
        return ApiResult.success("合并成功。");
    }

    @Override
    public Boolean updateByIdAsync(BusEstateAgentVO vo, ExtEmployee emp) {
        return updateById(vo, emp);
    }

    @Override
    public ApiResult recommend(String[] ids, Integer isRecommend) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var obj = baseMapper.selectById(ids[i]);
            if (obj != null) {
                obj.setIsRecommend(isRecommend == 1 ? true : false);
                updateById(obj);
            }
        }
        return ApiResult.success("设置成功。");
    }

}
