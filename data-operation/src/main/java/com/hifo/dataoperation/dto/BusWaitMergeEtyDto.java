package com.hifo.dataoperation.dto;


import com.hifo.dataoperation.entity.mongo.BusWaitMergeEty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 待建合并查询交互对象
 * @author weisibin
 * @date 2020年4月17日11:51:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusWaitMergeEtyDto extends BusWaitMergeEty{

	private static final long serialVersionUID = -3466133287567313259L;
	
	private String cityId;
	
	private String districtId;
}
