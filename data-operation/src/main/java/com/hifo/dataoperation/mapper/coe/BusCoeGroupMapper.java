package com.hifo.dataoperation.mapper.coe;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.coe.BusCoeGroup;
/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description
 */
public interface BusCoeGroupMapper extends BaseMapper<BusCoeGroup>{
	/**
	 * 计算fullcode
	 * @param pid
	 * @return
	 */
	public int countForFullcode(Long pid);
	

	

	
}
