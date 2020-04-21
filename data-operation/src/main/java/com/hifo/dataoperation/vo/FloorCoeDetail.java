package com.hifo.dataoperation.vo;

import java.util.Date;

import lombok.Data;
/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 楼层系数明细，对应一个楼层的具体系数
 */
@Data
public class FloorCoeDetail {
	private Long id;
	private Integer floorNo;
	private float coe;
	private Date createTime;
	/**
	 * 此系数是总楼层为floorTotalNo时的系数
	 */
	private Integer floorTotalNo;
}
