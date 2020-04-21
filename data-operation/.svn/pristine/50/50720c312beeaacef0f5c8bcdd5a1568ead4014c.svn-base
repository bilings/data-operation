package com.hifo.dataoperation.service.coe.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.coe.BusCoeGroup;
import com.hifo.dataoperation.entity.coe.BusCoeItem;
import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.mapper.coe.BusCoeStructureMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.BusCoeGroupService;
import com.hifo.dataoperation.service.coe.BusCoeItemService;
import com.hifo.dataoperation.service.coe.BusCoeStructureService;
import com.hifo.dataoperation.service.coe.CoeService;
import com.hifo.dataoperation.service.coe.enums.CoeGroupHasData;
import com.hifo.dataoperation.service.coe.enums.CoeType;
import com.hifo.dataoperation.service.coe.exception.IllegalCoeItemException;
import com.hifo.dataoperation.util.EntityUtils;
import com.hifo.dataoperation.vo.AreaCoe;
import com.hifo.dataoperation.vo.AreaCoeDetail;
import com.hifo.dataoperation.vo.CoeStructureParam;
import com.hifo.dataoperation.vo.CoeStructureRes;
import com.hifo.dataoperation.vo.FloorCoe;
import com.hifo.dataoperation.vo.FloorCoeDetail;
import com.hifo.dataoperation.vo.OrientationCoe;
import com.hifo.dataoperation.vo.OrientationCoeDetail;
import com.hifo.dataoperation.vo.SceneryCoe;
import com.hifo.dataoperation.vo.SceneryCoeDetail;
import com.hifo.dataoperation.vo.StructureCoe;
import com.hifo.dataoperation.vo.StructureCoeDetail;

import cn.hutool.core.text.StrSpliter;
import lombok.Data;

/**
 * 
 * @author 杨捷
 * @Date 2019年4月28日
 * @Description 通用系数结构相关服务实现
 */
@Service
public class BusCoeStructureServiceImpl extends MybatisPlusService<BusCoeStructureMapper, BusCoeStructure>
		implements BusCoeStructureService {
	
	@Data
	private  class ItemEntry {
		private String id;
		private String name;
	}

	@Autowired
	private CoeService<OrientationCoe,OrientationCoeDetail> orientationCoeService;
	@Autowired
	private CoeService<FloorCoe,FloorCoeDetail> floorCoeServiceImpl;
	@Autowired
	private CoeService<AreaCoe,AreaCoeDetail> areaCoeServiceImpl;
	@Autowired
	private CoeService<StructureCoe,StructureCoeDetail> structureCoeServiceImpl;
	@Autowired
	private CoeService<SceneryCoe,SceneryCoeDetail> sceneryCoeServiceImpl;

	@Autowired
	private BusCoeItemService busCoeItemService;
	@Autowired
	private BasicService basicService;
	@Autowired
	private BusCoeGroupService busCoeGroupService;
	
	

	@Override
	@Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
	public BusCoeStructure addCoeStructure(CoeStructureParam param) throws IllegalCoeItemException {
		if (param != null) {

			BusCoeStructure entity = new BusCoeStructure();
			EntityUtils.copyProperties(param, entity);
			entity.setOrganizationId(basicService.getLoginEmployee().getOrganizationId().longValue());

			boolean res = this.save(entity);

			Integer coeType = param.getCoeType();
			boolean coeSaveRes = false;
			// 系数类型是朝向系数
			if (coeType.intValue() == CoeType.ORIENTATION.getCode().intValue()) {
				coeSaveRes = orientationCoeService.initAndSaveCoe(entity);
			}
			// 系数类型是楼层系数
			if (coeType.intValue() == CoeType.FLOOR.getCode().intValue()) {
				coeSaveRes = floorCoeServiceImpl.initAndSaveCoe(entity);
			}
			// 系数类型是面积系数
			if (coeType.intValue() == CoeType.AREA.getCode().intValue()) {
				coeSaveRes = areaCoeServiceImpl.initAndSaveCoe(entity);
			}
			// 系数类型是建筑结构系数
			if (coeType.intValue() == CoeType.STRUCTURE.getCode().intValue()) {
				coeSaveRes = structureCoeServiceImpl.initAndSaveCoe(entity);
			}
			// 系数类型是景观系数
			if (coeType.intValue() == CoeType.SCENERY.getCode().intValue()) {
				coeSaveRes = sceneryCoeServiceImpl.initAndSaveCoe(entity);
			}

			// 系数项不为空时，保存系数项
			if (param.getDictionaryIds() != null || param.getConditionRangeEnd() != null
					|| param.getConditionRangeStart() != null) {
				BusCoeItem item = new BusCoeItem();
				item.setDictionaryIds(param.getDictionaryIds());
				item.setRangEnd(param.getConditionRangeEnd());
				item.setRangStart(param.getConditionRangeStart());
				item.setCoeStructureId(entity.getId());
				item.setDictionaryNames(param.getDictionaryNames());

				checkItem(item,param.getPid());
				busCoeItemService.save(item);
			}

			if (res && coeSaveRes) {
				return entity;
			}
		}

		return null;
	}

	@Override
	@Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
	public boolean deleteStructure(Long id) {
		
		BusCoeStructure structure=this.baseMapper.selectById(id);       
		Long pId = structure.getPid();
		boolean delRes2 = busCoeItemService.deleteByStruectureId(id);
		boolean delRes = this.removeById(id);
		//是最上级结构，需要对系数组做相应处理
		if(pId==null) {
			BusCoeGroup group=busCoeGroupService.getById(structure.getGroupId());
			QueryWrapper<BusCoeGroup> queryWrapper = new QueryWrapper<BusCoeGroup>();
			queryWrapper.eq("pid", structure.getGroupId());
			BusCoeGroup childGroup=busCoeGroupService.getOne(queryWrapper);
			//如果当前系数组有子系数组，则不能删除，要设置为无数据状态
			if(childGroup!=null) {
				group.setHasdata(CoeGroupHasData.YES.getCode());
				busCoeGroupService.saveOrUpdate(group);
			}
			//当前系数组没有子系数组，则直接删除
			else {
				busCoeGroupService.removeById(structure.getGroupId());
				//父系数组无数据，且当前要删除的系数组无兄弟系数组，则删除父级系数组
				QueryWrapper<BusCoeGroup> queryParentGroupWrapper = new QueryWrapper<BusCoeGroup>();
				queryParentGroupWrapper.eq("id", group.getpId());
				BusCoeGroup parentGroup=busCoeGroupService.getOne(queryParentGroupWrapper);
				//父系数组无数据
				if(parentGroup!=null && parentGroup.getHasdata()==CoeGroupHasData.NO.getCode().intValue()) {
					QueryWrapper<BusCoeGroup> queryBrotherWrapper = new QueryWrapper<BusCoeGroup>();
					queryBrotherWrapper.eq("pid", group.getpId());
					List<BusCoeGroup> brotherList=busCoeGroupService.list(queryBrotherWrapper);
					//无兄弟系数组
					if(brotherList==null || brotherList.isEmpty()) {
						busCoeGroupService.removeById(group.getpId());
					}
				}
				
			}
		}
		
	
		return delRes  && delRes2;
	}

	@Override
	public List<CoeStructureRes> getCoeStructureTree(Long groupId) {
		QueryWrapper<BusCoeStructure> queryWrapper = new QueryWrapper<BusCoeStructure>();
		queryWrapper.eq("groupid", groupId).eq("organizationId",
				basicService.getLoginEmployee().getOrganizationId().longValue());
		List<BusCoeStructure> structurelist = this.baseMapper.selectList(queryWrapper);
		if (structurelist != null && !structurelist.isEmpty()) {
			List<BusCoeItem> itemList = busCoeItemService.getBusCoeItemList(groupId);
			List<CoeStructureRes> res = new ArrayList<CoeStructureRes>();
			Map<Long, List<BusCoeItem>> itemMap = initItemMap(itemList);
			for (BusCoeStructure structure : structurelist) {
				CoeStructureRes resDetail = convertToCoeStructureDto(structure,itemMap.get(structure.getId()));
				res.add(resDetail);
			}
			return res;
		}

		return null;
	}
	
	private CoeStructureRes convertToCoeStructureDto(BusCoeStructure structure, List<BusCoeItem> items) {
		CoeStructureRes resDetail = new CoeStructureRes();
		EntityUtils.copyProperties(structure, resDetail);
		resDetail.setItems(items);
		resDetail.setKey(structure.getId());
		resDetail.setParent(structure.getPid());
		return resDetail;
	}

	private Map<Long, List<BusCoeItem>> initItemMap(List<BusCoeItem> itemList) {
		Map<Long, List<BusCoeItem>> map = new HashMap<Long, List<BusCoeItem>>(8);
		if (itemList != null && !itemList.isEmpty()) {
			for (BusCoeItem busCoeItem : itemList) {
				List<BusCoeItem> tempList = map.get(busCoeItem.getCoeStructureId());
				if (tempList == null) {
					tempList = new ArrayList<BusCoeItem>();
				}
				tempList.add(busCoeItem);
				map.put(busCoeItem.getCoeStructureId(), tempList);
			}
		}
		return map;
	}

	@Override
	public boolean editCoeStructure(CoeStructureParam param) throws IllegalCoeItemException {
		BusCoeItem item = new BusCoeItem();
		item.setDictionaryIds(param.getDictionaryIds());
		item.setRangEnd(param.getConditionRangeEnd());
		item.setRangStart(param.getConditionRangeStart());
		item.setCoeStructureId(param.getId());
		item.setDictionaryNames(param.getDictionaryNames());

		checkItem(item,param.getPid());

		QueryWrapper<BusCoeItem> queryWrapper = new QueryWrapper<BusCoeItem>();
		queryWrapper.eq("coeStructureId", param.getId());
		return busCoeItemService.update(item, queryWrapper);

	}

	private void checkItem(BusCoeItem item,Long coeStructurePid) throws IllegalCoeItemException {
		if (item == null) {
			throw new IllegalCoeItemException("");
		} else {
			String ids = item.getDictionaryIds();
			// 系数项存的是字典表id
			if (ids != null && !ids.isEmpty()) {
				List<ItemEntry> list = splitItem(item);
				StringBuffer sb = new StringBuffer();
				boolean needBreak=false;
				for (ItemEntry itemEntry : list) {
					List<String> dictionaryIds= busCoeItemService.checkIllegalDictionaryItem(item.getCoeStructureId(), itemEntry.getId(),coeStructurePid);
					if ( dictionaryIds!=null  && !dictionaryIds.isEmpty()) {
						for (String dictionaryId : dictionaryIds) {
							if(needBreak) {
								break;
							}
							List<String> split = StrSpliter.split(dictionaryId, ',', 0, true, true);
							for (String oldItemId : split) {
								if(oldItemId.equals(itemEntry.getId())) {
									sb.append(itemEntry.getName() + ",");
									needBreak=true;
									break;
								}
							}
						}
					}
				}
				String errorMsg = sb.toString();
				if (errorMsg != null && !errorMsg.isEmpty()) {
					throw new IllegalCoeItemException(errorMsg + "等项非法，请检查分组是否重复！");
				}
			}
			// 系数项存的是范围值
			else {
				Float start = item.getRangStart();
				Float end = item.getRangEnd();
				int checkRes = busCoeItemService.checkIllegalRangeItem(item.getCoeStructureId(), start, end,coeStructurePid);
				if (checkRes > 0) {
					throw new IllegalCoeItemException(item.getRangStart() + "-" + item.getRangEnd() + "非法，请检查分组是否重复！");
				}
			}
		}
	}

	private List<ItemEntry> splitItem(BusCoeItem item) {
		if (item != null && item.getDictionaryIds() != null && item.getDictionaryNames() != null) {
			String[] ids = item.getDictionaryIds().split(",");
			String[] names = item.getDictionaryNames().split(",");
			List<ItemEntry> res = new ArrayList<ItemEntry>();
			for (int i = 0; i < ids.length; i++) {
				ItemEntry entry = new ItemEntry();
				entry.setId(ids[i]);
				entry.setName(names[i]);
				res.add(entry);
			}
			return res;
		}
		return null;
	}

	@Override
	public CoeStructureRes getStructureById(Long structureId) {
		BusCoeStructure entity = this.getById(structureId);
		List<BusCoeItem> items = busCoeItemService.getItemsByStructureId(structureId);
		return convertToCoeStructureDto(entity,items);
	}


}


