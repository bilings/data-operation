package com.hifo.dataoperation.service.coe;

import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.vo.FloorCoe;

/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 楼层系数相关服务
 */
public interface FloorCoeService {
/**
 * 初始化并保存一个结构下的系数
 * @param structure
 * @return
 */
	public boolean initAndSaveCoe(BusCoeStructure structure);
	
	/**
	 * 保存一个结构下的系数
	 * @param coe
	 * @return
	 */
	public boolean saveCoe(FloorCoe coe);
	/**
	 * 获取一个结构下的系数
	 * @param structureId
	 * @return
	 */
	public FloorCoe getCoeByStructureId(Long structureId);
}
