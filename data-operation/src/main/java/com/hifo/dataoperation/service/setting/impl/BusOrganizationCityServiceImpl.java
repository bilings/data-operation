package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.BusOrganizationCity;
import com.hifo.dataoperation.mapper.setting.BusOrganizationCityMapper;
import com.hifo.dataoperation.service.setting.BusOrganizationCityService;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评估机构有权限的城市业务类
 *
 * @Author: xmw
 * @Date: 2019/5/8 18:11
 */
@Service
public class BusOrganizationCityServiceImpl extends MybatisPlusService<BusOrganizationCityMapper, BusOrganizationCity> implements BusOrganizationCityService {

    @Override
    public List<BusOrganizationCity> listCityByUser(int organizationId) {
        return baseMapper.selectList(new QueryWrapper<BusOrganizationCity>().eq("organizationId", organizationId));
    }

    @Override
    public BusOrganizationCity updateCityById(Long id) {
        var city = baseMapper.selectById(id);
        if (city != null) {
            var list = baseMapper.selectList(new QueryWrapper<BusOrganizationCity>().eq("organizationId", city.getOrganizationId()));
            list.stream().forEach(e -> {
                e.setIsUse(false);
                updateById(e);
            });
            city.setIsUse(true);
            updateById(city);
        }
        return city;
    }

}
