package com.hifo.dataoperation.mapper.coe;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCommonCoeFloor;

/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description
 */
public interface BusCommonCoeFloorMapper extends BaseMapper<BusCommonCoeFloor> {
	/**
	 * 获取父级分组对应的第一级结构的楼层系数
	 * 
	 * @param parentGroupId
	 * @return
	 */
	public List<BusCommonCoeFloor> getParentFloorCoe(Long parentGroupId);
	/**
	 * 获取一个系数组下的所有楼层系数
	 * @param groupId
	 * @return
	 */
	public List<BusCommonCoeFloor> getFloorCoeByGroup(Long groupId);
}
