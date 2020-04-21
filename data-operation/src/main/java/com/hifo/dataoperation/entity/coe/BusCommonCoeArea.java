package com.hifo.dataoperation.entity.coe;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @author 杨捷
 * @date 2019年5月6日
 * @description
 */

@TableName( "bus_common_coe_area")
public class BusCommonCoeArea extends BaseBusCommonCoe  implements Comparable<BusCommonCoeArea>{
	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	private Float area;

	@Override
	public int compareTo(BusCommonCoeArea arg0) {
		if(this.getArea() - arg0.getArea()>0) {
			return 1;
		}
		return -1;
	}
}
