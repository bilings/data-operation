package com.hifo.dataoperation.service.coe;

import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.vo.BaseCoe;
/**
 * 
 * @author 杨捷
 * @param <D>
 * @date 2019年5月5日
 * @description
 */
public interface CoeService<T extends BaseCoe<D>,D> {
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
	public boolean saveCoe(T coe);
	/**
	 * 获取一个结构下的系数
	 * @param structureId
	 * @return
	 */
	public T getCoeByStructureId(Long structureId);
}
