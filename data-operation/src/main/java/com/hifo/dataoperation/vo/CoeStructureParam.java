package com.hifo.dataoperation.vo;

import lombok.Data;

/**
 * 
 * @author 杨捷
 * @Date 2019年4月28日
 * @Description 系数结构对象参数
 */
@Data
public class CoeStructureParam {
	private Long id;
	/**
	 * 1：楼层系数:2：朝向系数:3：建筑结构系数:4：面积系数
	 */
	private int coeType;
	private Long pid;
	private Long groupId;
	private String fullcode;
	private String name;
	/**
	 * 1:整体区域,2:物业品质，3:有无电梯，4:建筑类别，5:建成年代，6:主力面积
	 */
	private int conditionType;
	/**
	 * 对应的条件项字典表id集合，用逗号分隔（13,12,11,19）对应的是（联排别墅，花园洋房，高档公寓，低层）
	 */
	private String dictionaryIds;
	
	private String dictionaryNames;
	
	/**
	 * 范围条件项起始值
	 */
	private Float conditionRangeStart;
	/**
	 * 范围条件项结束值
	 */
	private Float conditionRangeEnd;
	
//	private Long organizationId;
}
