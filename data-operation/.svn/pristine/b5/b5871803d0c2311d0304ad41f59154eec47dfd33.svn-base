package com.hifo.dataoperation.mapper.coe;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCommonCoeArea;

/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description
 */
public interface BusCommonCoeAreaMapper  extends BaseMapper<BusCommonCoeArea>{
	/**
	 * 获取父级分组对应的第一级结构的楼层系数
	 * @param parentGroupId
	 * @return
	 */
	public List<BusCommonCoeArea> getParentAreaCoe(Long parentGroupId);
	/**
	 * 获取一个系数组下所有的系数
	 * @param groupId
	 * @return
	 */
	public List<BusCommonCoeArea> getAreaCoeByGroup(Long groupId);
}
