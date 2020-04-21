package com.hifo.dataoperation.service.coe.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.coe.BusCoeItem;
import com.hifo.dataoperation.mapper.coe.BusCoeItemMapper;
import com.hifo.dataoperation.service.coe.BusCoeItemService;

/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
@Service
public class BusCoeItemServiceImpl extends MybatisPlusService<BusCoeItemMapper, BusCoeItem>
		implements BusCoeItemService {

	@Autowired
	private BusCoeItemMapper busCoeItemMapper;

	@Override
	public boolean deleteByStruectureId(Long struectureId) {
		QueryWrapper<BusCoeItem> queryWrapper = new QueryWrapper<BusCoeItem>();
		queryWrapper.eq("coeStructureId", struectureId);
		return this.remove(queryWrapper);
	}

	@Override
	public List<BusCoeItem> getBusCoeItemList(Long groupId) {
		return busCoeItemMapper.getBusCoeItemList(groupId);
	}

	@Override
	public List<String> checkIllegalDictionaryItem(Long struectureId, String dictionaryId,Long coeStructurePid) {
		return busCoeItemMapper.checkIllegalDictionaryItem(struectureId, dictionaryId,coeStructurePid);
	}

	@Override
	public int checkIllegalRangeItem(Long struectureId, Float rangeStart, Float rangeEnd,Long coeStructurePid) {
		return busCoeItemMapper.checkIllegalRangeItem(struectureId, rangeStart, rangeEnd,coeStructurePid);
	}

	@Override
	public List<BusCoeItem> getItemsByStructureId(Long structureId) {
		QueryWrapper<BusCoeItem> queryWrapper = new QueryWrapper<BusCoeItem>();
		queryWrapper.eq("coeStructureId", structureId);
		return this.baseMapper.selectList(queryWrapper);
	}

}
