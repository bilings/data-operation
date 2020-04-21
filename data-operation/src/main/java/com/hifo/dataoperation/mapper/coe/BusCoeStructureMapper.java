package com.hifo.dataoperation.mapper.coe;



import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.entity.coe.CoeStructureWithItem;
/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 系数结构数据访问对象
 */
public interface BusCoeStructureMapper  extends BaseMapper<BusCoeStructure>{
	/**
	 * 根据系数组id返回下属结构项的列表
	 * @param groupId
	 * @return
	 */
	public List<CoeStructureWithItem> getCoeStructureWithItemList(Long groupId);
}
