package com.hifo.dataoperation.service.coe;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.coe.BusCoeGroup;
import com.hifo.dataoperation.service.coe.exception.InsertCoeGroupException;
import com.hifo.dataoperation.vo.CoeGroupParam;

/**
 * 
 * @author 杨捷
 * @Date 2019年4月28日
 * @Description 通用系数分组相关服务
 */
public interface BusCoeGroupService extends IService<BusCoeGroup>{
/**
 *  新增一个系数分组
 * @param param
 * @return
 * @throws InsertCoeGroupException
 */
	public boolean addCoeGroup(CoeGroupParam param)  throws InsertCoeGroupException;
	/**
	 * 删除一个分组系数
	 * @param groupId
	 * @return
	 */
	public boolean deleteCoeGroup(Long groupId);
	
/**
 * 获取一种系数类型下的分组树
 * @param coeType
 * @param cityId
 * @return
 */
	public List<BusCoeGroup> getTreeByCity(Integer coeType, String cityId);
}
