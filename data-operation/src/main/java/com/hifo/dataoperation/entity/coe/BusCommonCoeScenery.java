package com.hifo.dataoperation.entity.coe;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description
 */
@TableName( "bus_common_coe_scenery")
public class BusCommonCoeScenery  extends BaseBusCommonCoe{
	public Long getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(Long sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	private Long sceneryId;
	private String sceneryName;
}
