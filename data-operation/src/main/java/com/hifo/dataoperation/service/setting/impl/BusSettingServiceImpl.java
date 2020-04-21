package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.BusSetting;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.setting.BusSettingMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.BusSettingService;
import com.hifo.dataoperation.vo.BusSettingVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 机构设置
 *
 * @author jinzhichen
 * @date 2019/4/12 15:22
 */
@Service
public class BusSettingServiceImpl extends MybatisPlusService<BusSettingMapper, BusSetting> implements BusSettingService {

    @Autowired
    private BasicService basicService;

    /**
     * 询价单-查询机构设置信息
     *
     * @param vo
     * @return
     */
    @Override
    public ApiResult query(BusSettingVO vo) {
        ExtEmployee extEmployee = basicService.getLoginEmployee();
        vo.setOrganizationId(extEmployee.getOrganizationId());
        return ApiResult.success(baseMapper.busSettingList(vo));
    }

    /**
     * 根据appid查询机构id srcret
     *
     * @param vo
     * @return
     */
    @Override
    public BusSetting queryByAppid(BusSetting vo) {
        QueryWrapper wrapper = new QueryWrapper<BusSettingVO>();
        if (StringUtils.isNotBlank(vo.getAppid())) {
            wrapper.eq("appid", vo.getAppid());
        }
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     * 询价单-保存或更新机构设置信息
     *
     * @param busSetting
     * @return
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult updateBusSetting(BusSetting busSetting) {
        ExtEmployee extEmployee = basicService.getLoginEmployee();
        busSetting.setOrganizationId(extEmployee.getOrganizationId());
        if (this.saveOrUpdate(busSetting)) {
            return ApiResult.success(busSetting);
        } else {
            return ApiResult.failed();
        }
    }

}
