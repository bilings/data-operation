 package com.hifo.dataoperation.dto;

import org.springframework.format.annotation.DateTimeFormat;

import com.hifo.dataoperation.entity.mongo.BusCaseInfoEty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼盘均价页面传出后台对象
 * @author weisibin
 * @date 2020年4月8日15:51:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusCaseInfoEtyDto extends BusCaseInfoEty {
	private static final long serialVersionUID = -8504316602995039749L;
	
    private String avgPriceId;//均价id
    @DateTimeFormat(pattern="yyyyMMdd")
    private String caseDateStart;//价格开始时间
    @DateTimeFormat(pattern="yyyyMMdd")
    private String caseDateEnd;//价格结束时
    private String areaMin;//面积起
    private String areaMax;//面积止
   
}
