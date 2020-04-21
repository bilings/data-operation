package com.hifo.dataoperation.vo;

import lombok.Data;

/**
 * 
 * @author 杨捷
 * @date 2019年5月9日
 * @description
 */
@Data
public class CoeGroupParam {
	private Long cityId;
	private String cityName;
	private String administrativeId;
	private String areaId;
	private Integer estateId;
	private String name;
	/**
	 * 所属系数类型（1：楼层系数:2：朝向系数:3：建筑结构系数:4：面积系数;5:景观系数）
	 */
	private Integer coeType;
}
