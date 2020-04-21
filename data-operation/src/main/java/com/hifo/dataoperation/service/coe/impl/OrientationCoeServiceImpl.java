package com.hifo.dataoperation.service.coe.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.entity.coe.BusCommonCoeOrientation;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeOrientationMapper;
import com.hifo.dataoperation.vo.OrientationCoe;
import com.hifo.dataoperation.vo.OrientationCoeDetail;

/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
@Service("orientationCoeServiceImpl")
public class OrientationCoeServiceImpl extends BaseCoeServiceImpl<BusCommonCoeOrientationMapper,BusCommonCoeOrientation,OrientationCoe,OrientationCoeDetail>{
	@Autowired
	private BusCommonCoeOrientationMapper busCommonCoeOrientationMapper;
	
	@Override
	protected List<BusCommonCoeOrientation> childToEntity(OrientationCoe coe,
			List<BusCommonCoeOrientation> entityList) {
		return entityList;
	}
	
	@Override
	protected OrientationCoe childToDto(List<BusCommonCoeOrientation> detailList, Long structureId,
			OrientationCoe dto) {
		return dto;
	}

	@Override
	protected List<BusCommonCoeOrientation> getRootCoe(long parentGroupId) {
		return busCommonCoeOrientationMapper.getParentOrientationCoe(parentGroupId);
	}

}
