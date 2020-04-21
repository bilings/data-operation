package com.hifo.dataoperation.service.coe.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.entity.coe.BusCommonCoeStructure;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeStructureMapper;
import com.hifo.dataoperation.vo.StructureCoe;
import com.hifo.dataoperation.vo.StructureCoeDetail;

/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description 建筑结构系数服务实现类
 */
@Service("structureCoeServiceImpl")
public class StructureCoeServiceImpl extends BaseCoeServiceImpl<BusCommonCoeStructureMapper,BusCommonCoeStructure,StructureCoe,StructureCoeDetail>{
	@Autowired
	private BusCommonCoeStructureMapper busCommonCoeStructureMapper;

	@Override
	protected List<BusCommonCoeStructure> getRootCoe(long parentGroupId) {
		return  this.busCommonCoeStructureMapper.getParentStructureCoe(parentGroupId);
	}

	@Override
	protected List<BusCommonCoeStructure> childToEntity(StructureCoe coe, List<BusCommonCoeStructure> entityList) {
		return entityList;
	}

	@Override
	protected StructureCoe childToDto(List<BusCommonCoeStructure> detailList, Long structureId, StructureCoe dto) {
		return dto;
	}

}
