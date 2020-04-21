package com.hifo.dataoperation.entity.coe;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系数结构带系数项信息
 * @author 杨捷
 * @date 2019年6月5日
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CoeStructureWithItem extends BusCoeStructure {
	private Long itemid;
	private String dictionaryIds;
	private String dictionaryNames;
	private Float rangStart;
	private Float rangEnd;
}
