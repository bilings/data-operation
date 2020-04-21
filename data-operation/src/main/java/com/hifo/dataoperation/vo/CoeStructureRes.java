package com.hifo.dataoperation.vo;

import java.util.List;

import com.hifo.dataoperation.entity.coe.BusCoeItem;

import lombok.Data;

/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 系数结构查询返回对象
 */ 
@Data
public class CoeStructureRes {
	private Long key;
	private Long parent;
	private int coeType;
	private String fullcode;
	private String name;
	private int conditionType;
	private Long groupid;
	/**
	 * 系数项列表
	 */
	private List<BusCoeItem> items;
	
	/**
	 * 是否key删除，如果有下级节点不能删除，如果是城市级的根节点，不能删除
	 */
	private boolean deleteAble;
}
