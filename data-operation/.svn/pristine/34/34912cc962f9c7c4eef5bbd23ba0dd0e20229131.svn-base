package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.BusOrganizationCity;

import java.util.List;

/**
 * 评估机构有权限的城市业务接口
 *
 * @Author: xmw
 * @Date: 2019/5/8 18:11
 */
public interface BusOrganizationCityService extends IService<BusOrganizationCity> {

    /**
     * 获取当前用户城市
     *
     * @param organizationId
     * @return
     */
    List<BusOrganizationCity> listCityByUser(int organizationId);

    /**
     * 修改当前机构默认城市
     *
     * @param id
     * @return
     */
    BusOrganizationCity updateCityById(Long id);
}
