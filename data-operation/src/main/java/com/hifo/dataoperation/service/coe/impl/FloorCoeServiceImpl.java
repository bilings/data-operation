package com.hifo.dataoperation.service.coe.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.entity.coe.BusCommonCoeFloor;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeFloorMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.util.EntityUtils;
import com.hifo.dataoperation.vo.FloorCoe;
import com.hifo.dataoperation.vo.FloorCoeDetail;
import com.hifo.dataoperation.vo.FloorCoeGroupByTotalNo;

/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
@Service("floorCoeServiceImpl")
public class FloorCoeServiceImpl
		extends BaseCoeServiceImpl<BusCommonCoeFloorMapper, BusCommonCoeFloor, FloorCoe, FloorCoeDetail> {
	@Autowired
	private BusCommonCoeFloorMapper busCommonCoeFloorMapper;
	@Autowired
	private BasicService basicService;

	@Override
	protected List<BusCommonCoeFloor> childToEntity(FloorCoe coe, List<BusCommonCoeFloor> entityList) {
		List<FloorCoeGroupByTotalNo> groupList = coe.getGroups();
		List<BusCommonCoeFloor> res = new ArrayList<BusCommonCoeFloor>();

		for (FloorCoeGroupByTotalNo floorCoeGroupByTotalNo : groupList) {
			List<FloorCoeDetail> detailList = floorCoeGroupByTotalNo.getDetailList();
			String desc = coe.getDescription();
			Long structureId = coe.getStructureId();

			for (FloorCoeDetail detail : detailList) {
				BusCommonCoeFloor entity = new BusCommonCoeFloor();
				EntityUtils.copyProperties(detail, entity);
				entity.setDescription(desc);
				entity.setCoeStructureId(structureId);
				entity.setCreateTime(new Date(System.currentTimeMillis()));
				entity.setCoe(entity.getCoe());
				entity.setOrganizationId(basicService.getLoginEmployee().getOrganizationId().longValue());
				res.add(entity);
			}
		}
		return res;
	}

	@Override
	protected FloorCoe childToDto(List<BusCommonCoeFloor> detailList, Long structureId, FloorCoe dto) {
		if (detailList != null && !detailList.isEmpty()) {
			List<FloorCoeGroupByTotalNo> groups = new ArrayList<>();
			Map<Integer, List<BusCommonCoeFloor>> map = new HashMap<Integer, List<BusCommonCoeFloor>>();
			for (BusCommonCoeFloor entity : detailList) {
				Integer totalNo = entity.getFloorTotalNo();
				List<BusCommonCoeFloor> tempList = map.get(totalNo);
				if (tempList == null) {
					tempList = new ArrayList<>();
				}
				tempList.add(entity);
				map.put(totalNo, tempList);
			}
			int size = map.keySet().size();
			for (int i = 1; i <= size; i++) {
				FloorCoeGroupByTotalNo group = new FloorCoeGroupByTotalNo();
				group.setTotalNo(i);
				List<BusCommonCoeFloor> entityList = map.get(i);
				List<FloorCoeDetail> dtoDetailList = convertToDtoList(entityList);
				group.setDetailList(dtoDetailList);

				groups.add(group);
			}
			dto.setGroups(groups);
			dto.setDetailList(null);
		}
		return dto;
	}

	private List<FloorCoeDetail> convertToDtoList(List<BusCommonCoeFloor> entityList) {
		if (entityList != null) {
			List<FloorCoeDetail> res = new ArrayList<>();
			for (BusCommonCoeFloor busCommonCoeFloor : entityList) {
				FloorCoeDetail dto = new FloorCoeDetail();
				EntityUtils.copyProperties(busCommonCoeFloor, dto);
				res.add(dto);
			}
			return res;
		}

		return null;
	}

	@Override
	protected List<BusCommonCoeFloor> getRootCoe(long parentGroupId) {
		return this.busCommonCoeFloorMapper.getParentFloorCoe(parentGroupId);
	}

}
