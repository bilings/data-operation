package com.hifo.dataoperation.service.coe;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.service.coe.exception.IllegalCoeItemException;
import com.hifo.dataoperation.vo.CoeStructureParam;
import com.hifo.dataoperation.vo.CoeStructureRes;

/**
 * 
 * @author 杨捷
 * @Date 2019年4月28日
 * @Description 通用系数结构相关服务
 */
public interface BusCoeStructureService extends IService<BusCoeStructure>{
/**
 * 新增系数结构,自动新建默认的系数，系数继承上级
 * @param param
 * @return
 * @throws IllegalCoeItemException
 */
	public BusCoeStructure addCoeStructure(CoeStructureParam param) throws IllegalCoeItemException;
	
/**
 * 编辑系数结构
 * @param param
 * @return
 * @throws IllegalCoeItemException
 */
	public boolean editCoeStructure(CoeStructureParam param) throws IllegalCoeItemException;
	
	/**
	 * 根据id删除一个结构，如果有下级不能删除,删除结构时，自动删除对应系数
	 * @param id
	 * @return
	 */
	public boolean deleteStructure(Long id);
	
	/**
	 * 获取一个分组下的结构树
	 * @param groupId
	 * @return
	 */
	public List<CoeStructureRes> getCoeStructureTree(Long groupId);
	/**
	 * 根据结构id获取结构
	 * @param structureId
	 * @return
	 */
	public CoeStructureRes getStructureById(Long structureId) ;
}
