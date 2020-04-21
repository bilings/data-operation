package com.hifo.dataoperation.mapper.coe;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCommonCoeOrientation;

/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 朝向系数数据访问对象
 */
public interface BusCommonCoeOrientationMapper extends BaseMapper<BusCommonCoeOrientation> {
	/**
	 * 获取父级分组对应的第一级结构的朝向系数
	 * 
	 * @param parentGroupId
	 * @return
	 */
	public List<BusCommonCoeOrientation> getParentOrientationCoe(Long parentGroupId);
	/**
	 * 获取一个系数组下所有的系数
	 * @param groupId
	 * @return
	 */
	public List<BusCommonCoeOrientation> getOrientationCoeByGroup(Long groupId);
}
