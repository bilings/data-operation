package com.hifo.dataoperation.service.coe.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hifo.dataoperation.entity.coe.BusOrganizationCoetype;
import com.hifo.dataoperation.mapper.coe.BusOrganizationCoetypeMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.BusOrganizationCoetypeService;

import cn.hutool.core.date.DateUtil;

/**
 * <p>
 * 算法修正因素 服务实现类
 * </p>
 *
 * @author 杨捷
 * @since 2019-05-24
 */
@Service
public class BusOrganizationCoetypeServiceImpl extends ServiceImpl<BusOrganizationCoetypeMapper, BusOrganizationCoetype> implements BusOrganizationCoetypeService {

	@Autowired
	private BasicService basicService;
	
	@Override
	public BusOrganizationCoetype getCoeTypes(String administrativeId) {
		Integer organizationId = basicService.getLoginEmployee().getOrganizationId();
		QueryWrapper<BusOrganizationCoetype> queryWrapper = new QueryWrapper<BusOrganizationCoetype>();
		queryWrapper.eq("organization_Id", organizationId).eq("administrative_id", administrativeId);
		BusOrganizationCoetype entity =this.getOne(queryWrapper);
		return entity;
	}

	@Override
	public boolean setCoeType(BusOrganizationCoetype coeType) {
		coeType.setCreateTime(DateUtil.date());
		coeType.setCreateBy(basicService.getLoginEmployee().getName());
		coeType.setOrganizationId( basicService.getLoginEmployee().getOrganizationId());
		return this.saveOrUpdate(coeType);
	}

}
