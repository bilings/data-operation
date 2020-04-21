package com.hifo.dataoperation.mapper.coe;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCoeItem;
/**
 * 
 * @author 杨捷
 * @date 2019年4月30日
 * @description 
 */
public interface BusCoeItemMapper  extends BaseMapper<BusCoeItem>{
	/**
	 * 根据结构id删除对应的系数项
	 * @param struectureId
	 * @return
	 */
	public boolean deleteByStruectureId(Long struectureId);
	 /**
	  * 获取一个分组下的所有系数项
	  * @param groupId
	  * @return
	  */
	public List<BusCoeItem> getBusCoeItemList(Long groupId); 
/**
 * 校验一个带字典id的系数项是否合法，如果返回值大于0则为非法
 * @param struectureId
 * @param dictionaryId
 * @param coeStructurePid
 * @return
 */
	public List<String> checkIllegalDictionaryItem(Long struectureId, String dictionaryId, Long coeStructurePid);
	
/**
 * 校验一个带范围值的系数项是否合法，如果返回值大于0则为非法
 * @param struectureId
 * @param rangeStart
 * @param rangeEnd
 * @return
 */
	public int checkIllegalRangeItem(Long struectureId, Float rangeStart, Float rangeEnd, Long coeStructurePid);
}
