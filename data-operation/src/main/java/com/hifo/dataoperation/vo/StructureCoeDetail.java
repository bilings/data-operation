package com.hifo.dataoperation.vo;

import java.util.Date;

import lombok.Data;
/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description 建筑结构系数明细
 */
@Data
public class StructureCoeDetail {
	private Long structureId;
	private String structureName;
	private float coe;
	private Date createTime;
	private String description;
}
