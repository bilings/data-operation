package com.hifo.dataoperation.service.coe.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.entity.AvgPriceVo;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.coe.BaseBusCommonCoe;
import com.hifo.dataoperation.entity.coe.BusCoeGroup;
import com.hifo.dataoperation.entity.coe.BusCommonCoeArea;
import com.hifo.dataoperation.entity.coe.BusCommonCoeFloor;
import com.hifo.dataoperation.entity.coe.BusCommonCoeOrientation;
import com.hifo.dataoperation.entity.coe.BusCommonCoeScenery;
import com.hifo.dataoperation.entity.coe.BusCommonCoeStructure;
import com.hifo.dataoperation.entity.coe.CoeStructureWithItem;
import com.hifo.dataoperation.mapper.coe.BusCoeStructureMapper;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeAreaMapper;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeFloorMapper;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeOrientationMapper;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeSceneryMapper;
import com.hifo.dataoperation.mapper.coe.BusCommonCoeStructureMapper;
import com.hifo.dataoperation.service.coe.BusCoeGroupService;
import com.hifo.dataoperation.service.coe.CountCoeService;
import com.hifo.dataoperation.service.coe.enums.CoeGroupHasData;
import com.hifo.dataoperation.service.coe.enums.CoeStructureConditionType;
import com.hifo.dataoperation.service.coe.enums.CoeType;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.util.EntityUtils;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.core.util.NumberUtil;

/**
 * 
 * @author 杨捷
 * @date 2019年6月4日
 * @description
 */
@Service
public class CountCoeServiceImpl implements CountCoeService {

	@Autowired
	private BusEstateService busEstateService;
	@Autowired
	private BusCoeGroupService busCoeGroupService;
	@Autowired
	private BusCoeStructureMapper busCoeStructureMapper;
	@Autowired
	private BusCommonCoeFloorMapper busCommonCoeFloorMapper;
	@Autowired
	private BusCommonCoeOrientationMapper busCommonCoeOrientationMapper;
	@Autowired
	private BusCommonCoeStructureMapper busCommonCoeStructureMapper;
	@Autowired
	private BusCommonCoeSceneryMapper busCommonCoeSceneryMapper;
	@Autowired
	private BusCommonCoeAreaMapper busCommonCoeAreaMapper;

	@Override
	public Map<Integer, Float> getCoeMap(AvgPriceVo vo) {
		Map<Integer, Float> res = new HashMap<Integer, Float>();
		// 建筑面积
		Float area = vo.getBuildArea();

		Integer floor = vo.getFloor();

		Integer floorTotalNo = vo.getFloorTotalNo();
		// 朝向(数据字典Id)
		Long orientation = vo.getOrientation();
		// 景观(数据字典Id)
		Long scenery = vo.getScenery();
		// 建筑结构(数据字典Id)
		Long structure = vo.getStructure();
		// 楼盘id
		Long estateId = vo.getEstateId();
		BusEstate estate = busEstateService.getById(estateId);
		if (estate == null) {
			return res;
		}
		// key:系数类型 1：楼层系数:2：朝向系数:3：建筑结构系数:4：面积系数;5:景观系数 value：系数组
		Map<Integer, BusCoeGroup> groupMap = getGroupMap(estate);

		// 如果总楼层和第几层不为空，则设置楼层系数
		if (floor != null && floorTotalNo != null) {
			BusCoeGroup floorGroup = groupMap.get(CoeType.FLOOR.getCode());
			Float floorCoe = getFloorCoe(floorGroup, vo);
			if (floorCoe != null) {
				res.put(CoeType.FLOOR.getCode(), floorCoe);
			}
		}
		// 如果朝向不为空，则设置朝向系数
		if (orientation != null) {
			BusCoeGroup orientationGroup = groupMap.get(CoeType.ORIENTATION.getCode());
			Float orientationCoe = getOrientationCoe(orientationGroup, vo);
			if (orientationCoe != null) {
				res.put(CoeType.ORIENTATION.getCode(), orientationCoe);
			}
		}
		// 如果建筑结构不为空，则设置建筑结构系数
		if (structure != null) {
			BusCoeGroup structureGroup = groupMap.get(CoeType.STRUCTURE.getCode());
			Float structureCoe = getStructureCoe(structureGroup, vo);
			if (structureCoe != null) {
				res.put(CoeType.STRUCTURE.getCode(), structureCoe);
			}
		}
		// 如果景观不为空，则设置景观系数
		if (scenery != null) {
			BusCoeGroup sceneryGroup = groupMap.get(CoeType.SCENERY.getCode());
			Float sceneryCoe = getSceneryCoe(sceneryGroup, vo);
			if (sceneryCoe != null) {
				res.put(CoeType.SCENERY.getCode(), sceneryCoe);
			}
		}
		// 如果建筑面积不为空，则设置面积系数
		if (area != null) {
			BusCoeGroup areaGroup = groupMap.get(CoeType.AREA.getCode());
			Float areaCoe = getAreaCoe(areaGroup, vo);
			if (areaCoe != null) {
				res.put(CoeType.AREA.getCode(), areaCoe);
			}
		}

		return res;
	}

	/**
	 * 获取面积系数
	 * 
	 * @param sceneryGroup
	 * @param vo
	 * @return
	 */
	private Float getAreaCoe(BusCoeGroup areaGroup, AvgPriceVo vo) {
		if (areaGroup != null) {
			Map<Long, List<CoeStructureWithItem>> childrenSceneryMap = new HashMap<Long, List<CoeStructureWithItem>>();
			Map<Long, CoeStructureWithItem> structureMap = new HashMap<Long, CoeStructureWithItem>();
			// 当前分组下所有结构的系数
			List<BusCommonCoeArea> areaCoeList = busCommonCoeAreaMapper.getAreaCoeByGroup(areaGroup.getId());
			Map<Long, List<BusCommonCoeArea>> areaCoeMap = this.initCoeMap(areaCoeList);
			CoeStructureWithItem root = new CoeStructureWithItem();
			initStructureAndCoe(childrenSceneryMap, structureMap, areaGroup, vo, root);
			CoeStructureWithItem res = root;
			this.getAvailableStructure(res, root, vo, childrenSceneryMap, structureMap);
			List<BusCommonCoeArea> coeList = areaCoeMap.get(res.getId());

			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			Float resCoe = getAreaCoeBase(coeList, vo);
			if (resCoe != null) {
				return resCoe;
			}

			// 在符合分组条件的系数列表中未找到对应楼层的系数,则向上递归遍历系数结构，尝试寻找对应的系数
			Float parentStructureCoe = serchAreaParentStructureForCoe(res, areaCoeMap, structureMap, vo);
			if (parentStructureCoe != null) {
				return parentStructureCoe;
			}
			// 在本系数组的所有结构中都没有找到对应的系数，则在父系数组中寻找
			// 系数组有父级系数组
			if (areaGroup.getpId() != null) {
				BusCoeGroup parentGroup = busCoeGroupService.getById(areaGroup.getpId());
				//从父级系数组尝试查询系数
				return getAreaCoe(parentGroup, vo);
			}
		}
		return null;
	}

	/**
	 * 获取景观系数
	 * 
	 * @param sceneryGroup
	 * @param vo
	 * @return
	 */
	private Float getSceneryCoe(BusCoeGroup sceneryGroup, AvgPriceVo vo) {
		if (sceneryGroup != null) {
			Map<Long, List<CoeStructureWithItem>> childrenSceneryMap = new HashMap<Long, List<CoeStructureWithItem>>();
			Map<Long, CoeStructureWithItem> structureMap = new HashMap<Long, CoeStructureWithItem>();
			// 当前分组下所有结构的系数
			List<BusCommonCoeScenery> sceneryCoeList = busCommonCoeSceneryMapper
					.getSceneryCoeByGroup(sceneryGroup.getId());
			Map<Long, List<BusCommonCoeScenery>> sceneryCoeMap = this.initCoeMap(sceneryCoeList);
			CoeStructureWithItem root = new CoeStructureWithItem();
			initStructureAndCoe(childrenSceneryMap, structureMap, sceneryGroup, vo, root);
			CoeStructureWithItem res = root;
			this.getAvailableStructure(res, root, vo, childrenSceneryMap, structureMap);
			List<BusCommonCoeScenery> coeList = sceneryCoeMap.get(res.getId());

			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			Float resCoe = getSceneryCoeBase(coeList, vo);
			if (resCoe != null) {
				return resCoe;
			}

			// 在符合分组条件的系数列表中未找到对应楼层的系数,则向上递归遍历系数结构，尝试寻找对应的系数
			Float parentStructureCoe = serchSceneryParentStructureForCoe(res, sceneryCoeMap, structureMap, vo);
			if (parentStructureCoe != null) {
				return parentStructureCoe;
			}
			// 在本系数组的所有结构中都没有找到对应的系数，则在父系数组中寻找
			// 系数组有父级系数组
			if (sceneryGroup.getpId() != null) {
				BusCoeGroup parentGroup = busCoeGroupService.getById(sceneryGroup.getpId());
				//从父级系数组尝试查询系数
				return getSceneryCoe(parentGroup, vo);
			}
		}
		return null;
	}

	/**
	 * 获取建筑结构系数
	 * 
	 * @param floorGroup
	 * @param estate
	 * @param vo
	 * @return
	 */
	private Float getStructureCoe(BusCoeGroup structureGroup, AvgPriceVo vo) {
		if (structureGroup != null) {
			Map<Long, List<CoeStructureWithItem>> childrenStructureMap = new HashMap<Long, List<CoeStructureWithItem>>();
			Map<Long, CoeStructureWithItem> structureMap = new HashMap<Long, CoeStructureWithItem>();
			// 当前分组下所有结构的系数
			List<BusCommonCoeStructure> structureCoeList = busCommonCoeStructureMapper
					.getStructureCoeByGroup(structureGroup.getId());
			Map<Long, List<BusCommonCoeStructure>> structureCoeMap = this.initCoeMap(structureCoeList);
			CoeStructureWithItem root = new CoeStructureWithItem();
			initStructureAndCoe(childrenStructureMap, structureMap, structureGroup, vo, root);
			CoeStructureWithItem res = root;
			this.getAvailableStructure(res, root, vo, childrenStructureMap, structureMap);
			List<BusCommonCoeStructure> coeList = structureCoeMap.get(res.getId());

			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			Float resCoe = getStructureCoeBase(coeList, vo);
			if (resCoe != null) {
				return resCoe;
			}

			// 在符合分组条件的系数列表中未找到对应楼层的系数,则向上递归遍历系数结构，尝试寻找对应的系数
			Float parentStructureCoe = serchStructureParentStructureForCoe(res, structureCoeMap, structureMap, vo);
			if (parentStructureCoe != null) {
				return parentStructureCoe;
			}
			// 在本系数组的所有结构中都没有找到对应的系数，则在父系数组中寻找
			// 系数组有父级系数组
			if (structureGroup.getpId() != null) {
				BusCoeGroup parentGroup = busCoeGroupService.getById(structureGroup.getpId());
				// 从父级系数组尝试查询系数
				return getStructureCoe(parentGroup, vo);
			}
		}
		return null;
	}

	/**
	 * 获取朝向系数
	 * 
	 * @param floorGroup
	 * @param estate
	 * @param vo
	 * @return
	 */
	private Float getOrientationCoe(BusCoeGroup orientationGroup, AvgPriceVo vo) {
		if (orientationGroup != null) {
			Map<Long, List<CoeStructureWithItem>> childrenStructureMap = new HashMap<Long, List<CoeStructureWithItem>>();
			Map<Long, CoeStructureWithItem> structureMap = new HashMap<Long, CoeStructureWithItem>();
			// 当前分组下所有结构的系数
			List<BusCommonCoeOrientation> orientationCoeList = busCommonCoeOrientationMapper
					.getOrientationCoeByGroup(orientationGroup.getId());
			Map<Long, List<BusCommonCoeOrientation>> orientationCoeMap = this.initCoeMap(orientationCoeList);
			CoeStructureWithItem root = new CoeStructureWithItem();
			initStructureAndCoe(childrenStructureMap, structureMap, orientationGroup, vo, root);
			CoeStructureWithItem res = root;
			this.getAvailableStructure(res, root, vo, childrenStructureMap, structureMap);
			List<BusCommonCoeOrientation> coeList = orientationCoeMap.get(res.getId());

			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			Float resCoe = getOrientationCoeBase(coeList, vo);
			if (resCoe != null) {
				return resCoe;
			}

			// 在符合分组条件的系数列表中未找到对应楼层的系数,则向上递归遍历系数结构，尝试寻找对应的系数
			Float parentStructureCoe = serchOrientationParentStructureForCoe(res, orientationCoeMap, structureMap, vo);
			if (parentStructureCoe != null) {
				return parentStructureCoe;
			}
			// 在本系数组的所有结构中都没有找到对应的系数，则在父系数组中寻找
			// 系数组有父级系数组
			if (orientationGroup.getpId() != null) {
				BusCoeGroup parentGroup = busCoeGroupService.getById(orientationGroup.getpId());
				// 从父级系数组尝试查询系数
				return getOrientationCoe(parentGroup, vo);
			}
		}
		return null;
	}

	private <T extends BaseBusCommonCoe> Map<Long, List<T>> initCoeMap(List<T> coeList) {
		if (coeList != null) {
			Map<Long, List<T>> resMap = new HashMap<Long, List<T>>();
			for (T coeEntity : coeList) {
				List<T> tempList = resMap.get(coeEntity.getCoeStructureId());
				if (tempList == null) {
					tempList = new ArrayList<T>();
				}
				tempList.add(coeEntity);
				resMap.put(coeEntity.getCoeStructureId(), tempList);
			}
			return resMap;
		}

		return null;
	}
	
	private Float getFloorCoe(BusCoeGroup floorGroup, AvgPriceVo vo) {
		if (floorGroup != null) {
			Map<Long, List<CoeStructureWithItem>> childrenStructureMap = new HashMap<Long, List<CoeStructureWithItem>>();
			Map<Long, CoeStructureWithItem> structureMap = new HashMap<Long, CoeStructureWithItem>();
			// 当前分组下所有结构的系数
			List<BusCommonCoeFloor> floorCoeList = busCommonCoeFloorMapper.getFloorCoeByGroup(floorGroup.getId());
			
			Map<Long, List<BusCommonCoeFloor>> floorCoeMap =initCoeMap(floorCoeList);
			CoeStructureWithItem root = new CoeStructureWithItem();
			initStructureAndCoe(childrenStructureMap, structureMap, floorGroup, vo, root);
			CoeStructureWithItem res = root;
			// 从父节点开始遍历，得到最终符合条件的节点
			this.getAvailableStructure(res, root, vo, childrenStructureMap, structureMap);
			List<BusCommonCoeFloor> coeList = floorCoeMap.get(res.getId());

			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			Float resCoe = getFloorCoeBase(coeList, vo);
			if (resCoe != null) {
				return resCoe;
			}

			// 在符合分组条件的系数列表中未找到对应楼层的系数,则向上递归遍历系数结构，尝试寻找对应的系数
			Float parentStructureCoe = serchFloorParentStructureForCoe(res, floorCoeMap, structureMap, vo);
			if (parentStructureCoe != null) {
				return parentStructureCoe;
			}
			// 在本系数组的所有结构中都没有找到对应的系数，则在父系数组中寻找
			// 系数组有父级系数组
			if (floorGroup.getpId() != null) {
				BusCoeGroup parentGroup = busCoeGroupService.getById(floorGroup.getpId());
				// 从父级系数组尝试查询系数
				return getFloorCoe(parentGroup, vo);
			}
		}
		return null;
	}

	private Float serchAreaParentStructureForCoe(CoeStructureWithItem structure,
			Map<Long, List<BusCommonCoeArea>> areaCoeMap, Map<Long, CoeStructureWithItem> structureMap, AvgPriceVo vo) {
		CoeStructureWithItem parentStructure = structureMap.get(structure.getPid());
		if (parentStructure != null) {
			List<BusCommonCoeArea> tempCoeList = areaCoeMap.get(structure.getPid());
			Float resCoe = getAreaCoeBase(tempCoeList, vo);
			if (resCoe != null) {
				return resCoe;
			} else {
				return serchAreaParentStructureForCoe(parentStructure, areaCoeMap, structureMap, vo);
			}
		}
		return null;
	}

	private Float serchSceneryParentStructureForCoe(CoeStructureWithItem structure,
			Map<Long, List<BusCommonCoeScenery>> sceneryCoeMap, Map<Long, CoeStructureWithItem> structureMap,
			AvgPriceVo vo) {
		CoeStructureWithItem parentStructure = structureMap.get(structure.getPid());
		if (parentStructure != null) {
			List<BusCommonCoeScenery> tempCoeList = sceneryCoeMap.get(structure.getPid());
			Float resCoe = getSceneryCoeBase(tempCoeList, vo);
			if (resCoe != null) {
				return resCoe;
			} else {
				return serchSceneryParentStructureForCoe(parentStructure, sceneryCoeMap, structureMap, vo);
			}
		}
		return null;
	}

	private Float serchStructureParentStructureForCoe(CoeStructureWithItem structure,
			Map<Long, List<BusCommonCoeStructure>> structureCoeMap, Map<Long, CoeStructureWithItem> structureMap,
			AvgPriceVo vo) {
		CoeStructureWithItem parentStructure = structureMap.get(structure.getPid());
		if (parentStructure != null) {
			List<BusCommonCoeStructure> tempCoeList = structureCoeMap.get(structure.getPid());
			Float resCoe = getStructureCoeBase(tempCoeList, vo);
			if (resCoe != null) {
				return resCoe;
			} else {
				return serchStructureParentStructureForCoe(parentStructure, structureCoeMap, structureMap, vo);
			}
		}
		return null;
	}

	private Float serchOrientationParentStructureForCoe(CoeStructureWithItem structure,
			Map<Long, List<BusCommonCoeOrientation>> orientationCoeMap, Map<Long, CoeStructureWithItem> structureMap,
			AvgPriceVo vo) {
		CoeStructureWithItem parentStructure = structureMap.get(structure.getPid());
		if (parentStructure != null) {
			List<BusCommonCoeOrientation> tempCoeList = orientationCoeMap.get(structure.getPid());
			Float resCoe = getOrientationCoeBase(tempCoeList, vo);
			if (resCoe != null) {
				return resCoe;
			} else {
				return serchOrientationParentStructureForCoe(parentStructure, orientationCoeMap, structureMap, vo);
			}
		}
		return null;
	}

	private Float serchFloorParentStructureForCoe(CoeStructureWithItem structure,
			Map<Long, List<BusCommonCoeFloor>> floorCoeMap, Map<Long, CoeStructureWithItem> structureMap,
			AvgPriceVo vo) {
		CoeStructureWithItem parentStructure = structureMap.get(structure.getPid());
		if (parentStructure != null) {
			List<BusCommonCoeFloor> tempCoeList = floorCoeMap.get(structure.getPid());
			Float resCoe = getFloorCoeBase(tempCoeList, vo);
			if (resCoe != null) {
				return resCoe;
			} else {
				return serchFloorParentStructureForCoe(parentStructure, floorCoeMap, structureMap, vo);
			}
		}
		return null;
	}

	private Float getAreaCoeBase(List<BusCommonCoeArea> coeList, AvgPriceVo vo) {
		// 数组排序，面积小的系数排前面
		Collections.sort(coeList);
		Float voArea = vo.getBuildArea();
		Float voAreaCoe = getAreaCoeWithArea(voArea, coeList);
		
		Float standArea = vo.getStandAcreage();
		Float standAreaVo = getAreaCoeWithArea(standArea, coeList);
		
		if(standAreaVo!=null) {
			return NumberUtil.round(NumberUtil.div(voAreaCoe, standAreaVo), 4).floatValue();
		}

		return voAreaCoe;
	}
	
	private Float getAreaCoeWithArea(Float area,List<BusCommonCoeArea> coeList) {
		if(area == null) {
			return null;
		}
		
		BusCommonCoeArea maxArea = coeList.get(coeList.size() - 1);
		// 如果询价的房间面积大于设置的最大系数面积20%以上
		if (area > (maxArea.getArea().floatValue() * 1.2)) {
			return null;
		}
		// 如果询价的房间面积大于最大系数，但不超过20%
		if (area <= (maxArea.getArea().floatValue() * 1.2) && area > maxArea.getArea().floatValue()) {
			return maxArea.getCoe();
		}

		// 从符合分组条件的系数列表中遍历寻找对应面积的系数，如果找到了系数，直接返回系数
		for (int i = 0; i < coeList.size(); i++) {
			BusCommonCoeArea coe = coeList.get(i);

			Float coeArea = coe.getArea();
			if (coeArea.floatValue() == area.floatValue()) {
				return coe.getCoe();
			}
			
			BusCommonCoeArea nextCoe = coeList.get(i+1);
			float startArea=coe.getArea();
			float endArea=nextCoe.getArea();
			float startCoe=coe.getCoe();
			float endCoe=nextCoe.getCoe();

			if(coeArea<area && nextCoe.getArea()>area) {
				double subCoe = NumberUtil.sub(endCoe,startCoe);
				double subArea=  NumberUtil.sub(endArea,startArea);
				double subArea2 = NumberUtil.sub(area.floatValue(),startArea);
				double div =  NumberUtil.div(subCoe, subArea);
				double mul = NumberUtil.mul(div, subArea2);
				double result= NumberUtil.add(mul, startCoe);
				return NumberUtil.round(result, 4).floatValue();
			}
		}
		return null;
	}

	private Float getSceneryCoeBase(List<BusCommonCoeScenery> coeList, AvgPriceVo vo) {
		// 从符合分组条件的系数列表中遍历寻找对应景观的系数，如果找到了系数，直接返回系数
		for (BusCommonCoeScenery coe : coeList) {
			if (coe.getSceneryId().longValue() == vo.getScenery().longValue()) {
				return coe.getCoe();
			}
		}
		return null;
	}

	private Float getStructureCoeBase(List<BusCommonCoeStructure> coeList, AvgPriceVo vo) {
		// 从符合分组条件的系数列表中遍历寻找对应朝向的系数，如果找到了系数，直接返回系数
		for (BusCommonCoeStructure coe : coeList) {
			if (coe.getStructureId().longValue() == vo.getStructure().longValue()) {
				return coe.getCoe();
			}
		}
		return null;
	}

	private Float getOrientationCoeBase(List<BusCommonCoeOrientation> coeList, AvgPriceVo vo) {
		// 从符合分组条件的系数列表中遍历寻找对应朝向的系数，如果找到了系数，直接返回系数
		for (BusCommonCoeOrientation coe : coeList) {
			if (coe.getOrientationId().longValue() == vo.getOrientation().intValue()) {
				return coe.getCoe();
			}
		}
		return null;
	}

	private Float getFloorCoeBase(List<BusCommonCoeFloor> coeList, AvgPriceVo vo) {
		if(coeList!=null) {
			// 从符合分组条件的系数列表中遍历寻找对应楼层的系数，如果找到了系数，直接返回系数
			for (BusCommonCoeFloor busCommonCoeFloor : coeList) {
				Integer tempTotalNo = busCommonCoeFloor.getFloorTotalNo();
				Integer tempFloorNo = busCommonCoeFloor.getFloorNo();
				if (tempTotalNo.intValue() == vo.getFloorTotalNo().intValue()
						&& tempFloorNo.intValue() == vo.getFloor().intValue()) {
					return busCommonCoeFloor.getCoe();
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param childrenStructureMap
	 * @param structureMap
	 * @param coeMap               key：structureid value:coe
	 */
	private void initStructureAndCoe(Map<Long, List<CoeStructureWithItem>> childrenStructureMap,
			Map<Long, CoeStructureWithItem> structureMap, BusCoeGroup floorGroup, AvgPriceVo vo,
			CoeStructureWithItem root) {
		// 当前系数组下的所有结构
		List<CoeStructureWithItem> structureList = busCoeStructureMapper
				.getCoeStructureWithItemList(floorGroup.getId());
		for (CoeStructureWithItem coeStructureWithItem : structureList) {
			if (coeStructureWithItem.getPid() == null) {
				EntityUtils.copyProperties(coeStructureWithItem, root);
				;
			}
			Long pid = coeStructureWithItem.getPid();
			structureMap.put(coeStructureWithItem.getId(), coeStructureWithItem);
			List<CoeStructureWithItem> tempList = childrenStructureMap.get(pid);
			if (tempList == null) {
				tempList = new ArrayList<CoeStructureWithItem>();
			}
			tempList.add(coeStructureWithItem);
			childrenStructureMap.put(pid, tempList);
		}
	}

	/**
	 * 按照条件分组后， 获取最终可用的节点
	 * @param res
	 * @param root
	 * @param vo
	 * @param childrenStructureMapMap
	 * @param structureMap
	 */
	private void getAvailableStructure(CoeStructureWithItem res, CoeStructureWithItem root, AvgPriceVo vo,
			Map<Long, List<CoeStructureWithItem>> childrenStructureMapMap,
			Map<Long, CoeStructureWithItem> structureMap) {
		List<CoeStructureWithItem> structurelist = childrenStructureMapMap.get(root.getId());
		if (structurelist != null && !structurelist.isEmpty()) {
			for (CoeStructureWithItem coeStructureWithItem : structurelist) {
				// 当前节点是否符合要求
				boolean isStructure = checkStructure(coeStructureWithItem, vo);
				if (isStructure) {
					EntityUtils.copyProperties(coeStructureWithItem, res);
//					res = coeStructureWithItem;
					getAvailableStructure(res, coeStructureWithItem, vo, childrenStructureMapMap, structureMap);
				}
			}
		}
	}

	/**
	 *  检查此结构是否符合房间的条件
	 * @param struecure
	 * @param vo
	 * @return
	 */
	private boolean checkStructure(CoeStructureWithItem struecure, AvgPriceVo vo) {
		int typeInt = struecure.getConditionType();
		// 如果结构是物业品质
		if (typeInt == CoeStructureConditionType.QUALITY.getCode()) {
			Long quality = vo.getQuality();
			String dIds = struecure.getDictionaryIds();
			List<String> strList = StrSpliter.split(dIds, ',', 0, true, true);
			for (String str : strList) {
				long dLong = Long.valueOf(str);
				// 传入参数的物业品质与此结构的项对应，
				if (dLong == quality.longValue()) {
					return true;
				}
			}
		}
		if (typeInt == CoeStructureConditionType.HAS_Elevator.getCode()) {
			Integer haveLift = vo.getHaveLift();
			if (haveLift == null) {
				return false;
			}
			String dIds = struecure.getDictionaryIds();
			List<String> strList = StrSpliter.split(dIds, ',', 0, true, true);
			for (String str : strList) {
				int dInt = Integer.valueOf(str);
				// 传入参数的有无电梯与此结构的项对应，
				if (dInt == haveLift.intValue()) {
					return true;
				}
			}
		}
		// 建筑类别根据总楼层计算得到
		if (typeInt == CoeStructureConditionType.BUILDING_TYPE.getCode()) {
			Long buildingCategory = vo.getBuildingCategory();
			if (buildingCategory == null) {
				return false;
			}
			String dIds = struecure.getDictionaryIds();
			List<String> strList = StrSpliter.split(dIds, ',', 0, true, true);
			for (String str : strList) {
				// 传入参数的建筑类别与此结构的项对应
				if (buildingCategory.longValue() ==Long.parseLong(str)) {
					return true;
				}
			}
		}
		if (typeInt == CoeStructureConditionType.BUILDING_YEARS.getCode()) {
			int yearInt=checBuildingYearCondititon(struecure, vo);
			if(yearInt>0) {
				return true;
			}
			if(yearInt<0) {
				return false;
			}
		}
		if (typeInt == CoeStructureConditionType.MAIN_FLOOR_SPACE.getCode()) {
			int mainSpaceInt = checkMainSpaceCondition(struecure,vo);
			if(mainSpaceInt>0) {
				return true;
			}
		}
		return false;
	}
	
	private int checBuildingYearCondititon(CoeStructureWithItem struecure, AvgPriceVo vo) {

		Integer yearInt = vo.getBuildYear();
		if (yearInt == null) {
			return -1;
		}
		Float year = Float.parseFloat(yearInt.intValue() + "");
		Float start = struecure.getRangStart();
		Float end = struecure.getRangEnd();
		if (start == null) {
			if (year.floatValue() < end.floatValue()) {
				return 1;
			}
		} else if (end == null) {
			if (year.floatValue() > start.floatValue()) {
				return 1;
			}
		} else {
			if (year.floatValue() > start.floatValue() && year.floatValue() < end.floatValue()) {
				return 1;
			}
		}
		return 0;
	}
	
	private int checkMainSpaceCondition(CoeStructureWithItem struecure, AvgPriceVo vo) {
		Float mainArea = vo.getMainArea();
		if (mainArea == null) {
			return -1;
		}
		Float start = struecure.getRangStart();
		Float end = struecure.getRangEnd();
		if (start == null) {
			if (mainArea <= end.floatValue()) {
				return 1;
			}
		} else if (end == null) {
			if (mainArea > start.floatValue()) {
				return 1;
			}
		} else {
			if (mainArea > start.floatValue() && mainArea <= end.floatValue()) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 *  根据楼盘得到对应的系数组
	 * @param estate
	 * @return
	 */
	private Map<Integer, BusCoeGroup> getGroupMap(BusEstate estate) {
		if (estate != null) {
			Map<Integer, BusCoeGroup> res = new HashMap<Integer, BusCoeGroup>();

			//获取直接设置此楼盘的系数组
			QueryWrapper<BusCoeGroup> queryWrapper = new QueryWrapper<BusCoeGroup>();
//			queryWrapper.eq("estateid", estate.getId());
			List<BusCoeGroup> groupList = busCoeGroupService.list(queryWrapper);

			if (groupList != null && groupList.size() > 0) {
				for (BusCoeGroup busCoeGroup : groupList) {
					res.put(busCoeGroup.getCoeType(), busCoeGroup);
				}
				// 如果5个系数都设置了楼盘的系数组
				if (groupList.size() == EnumSet.allOf(CoeType.class).size()) {
					return res;
				}
			}
			for (int i = 1; i <= 5; i++) {
				// 根据楼盘id未取到系数组，则根据楼盘获取对应行政区域的系数组
				if (res.get(i) == null) {
					res.put(i, getAdministriveGroup(estate, i));
				}
			}
			return res;
		}
		return null;
	}

	/**
	 *  获取行政区域系数组
	 * @param estate
	 * @param coeType
	 * @return
	 */
	private BusCoeGroup getAdministriveGroup(BusEstate estate, Integer coeType) {
		BusCoeGroup districtGroup = getGroupByDistrict(estate, coeType);
		if (districtGroup != null) {
			return districtGroup;
		}
		// 楼盘所在区也没有设置系数组，则获取城市的 系数组
		return getBroupByCityId(estate, coeType);
	}

	private BusCoeGroup getBroupByCityId(BusEstate estate, Integer coeType) {
		QueryWrapper<BusCoeGroup> queryWrapper = new QueryWrapper<BusCoeGroup>();
		queryWrapper.eq("administrativeid", estate.getCityId()).eq("coeType", coeType);
		return busCoeGroupService.getOne(queryWrapper);
	}

	private BusCoeGroup getGroupByDistrict(BusEstate estate, Integer coeType) {
		QueryWrapper<BusCoeGroup> queryWrapper = new QueryWrapper<BusCoeGroup>();
		queryWrapper.eq("administrativeid", estate.getDistrictId()).eq("coeType", coeType).eq("hasdata",
				CoeGroupHasData.YES.getCode());
		return busCoeGroupService.getOne(queryWrapper);
	}

}
