package com.hifo.dataoperation.entity.coe;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @author 杨捷
 * @Date 2019年4月26日
 * @Description 朝向系数entity
 */
@TableName( "bus_common_coe_orientation")
public class BusCommonCoeOrientation extends BaseBusCommonCoe{
	public Long getOrientationId() {
		return orientationId;
	}
	public void setOrientationId(Long orientationId) {
		this.orientationId = orientationId;
	}
	public String getOrientationName() {
		return orientationName;
	}
	public void setOrientationName(String orientationName) {
		this.orientationName = orientationName;
	}
	private Long orientationId;
	private String orientationName;
}
