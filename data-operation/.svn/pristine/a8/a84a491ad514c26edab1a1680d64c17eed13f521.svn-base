package com.hifo.dataoperation.service.coe.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.entity.coe.BusCommonCoeArea;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeAreaMapper;
import com.hifo.dataoperation.vo.AreaCoe;
import com.hifo.dataoperation.vo.AreaCoeDetail;

/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
@Service("areaCoeServiceImpl")
public class AreaCoeServiceImpl extends BaseCoeServiceImpl<BusCommonCoeAreaMapper,BusCommonCoeArea,AreaCoe,AreaCoeDetail>{
	@Autowired
	private BusCommonCoeAreaMapper busCommonCoeAreaMapper;
	

	@Override
	protected List<BusCommonCoeArea> childToEntity(AreaCoe coe, List<BusCommonCoeArea> entityList) {
		return entityList;
	}

	@Override
	protected List<BusCommonCoeArea> getRootCoe(long parentGroupId) {
		return  this.busCommonCoeAreaMapper.getParentAreaCoe(parentGroupId);
	}

	@Override
	protected AreaCoe childToDto(List<BusCommonCoeArea> detailList, Long structureId, AreaCoe dto) {
		return dto;
	}

}
