package com.hifo.dataoperation.service.coe.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.entity.coe.BusCommonCoeScenery;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeSceneryMapper;
import com.hifo.dataoperation.vo.SceneryCoe;
import com.hifo.dataoperation.vo.SceneryCoeDetail;

/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description 建筑结构系数服务实现类
 */
@Service("sceneryCoeServiceImpl")
public class SceneryCoeServiceImpl extends BaseCoeServiceImpl<BusCommonCoeSceneryMapper,BusCommonCoeScenery,SceneryCoe,SceneryCoeDetail>{
	@Autowired
	private BusCommonCoeSceneryMapper busCommonCoeSceneryMapper;

	@Override
	protected List<BusCommonCoeScenery> getRootCoe(long parentGroupId) {
		return  this.busCommonCoeSceneryMapper.getParentSceneryCoe(parentGroupId);
	}

	@Override
	protected List<BusCommonCoeScenery> childToEntity(SceneryCoe coe, List<BusCommonCoeScenery> entityList) {
		return entityList;
	}

	@Override
	protected SceneryCoe childToDto(List<BusCommonCoeScenery> detailList, Long structureId, SceneryCoe dto) {
		return dto;
	}

}
