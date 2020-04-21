package com.hifo.dataoperation.entity.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 待建合并实体
 * @author weisibin
 * @date 2020年4月17日10:20:26
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection = "warehouse_community_waitmerge")
public class BusWaitMergeEty extends BaseEntity{
	private static final long serialVersionUID = 3315268185037448791L;
	
	/**
	 * 目标楼信息
	 */
	private String tId;//合并后的数据表id/目标楼盘
	private String tName;//目标楼盘名称
	private String tCityId;//目标楼盘城市id
	private String tCityName;//目标楼盘城市名称
	private String tDistrictId;//目标楼盘区域名称
	private String tDistrictName;//目标楼盘区域id
	private String tAddress;//目标楼盘地址
	private String tResource;//目标楼盘来源
	private String tCaseNo;//目标楼盘案例数
	
	/**
	 * 待合并楼盘信息
	 */
	private String wId;//合并后的数据表id/待合并楼盘
	private String wName;//待合并楼盘名称
	private String wCityId;//待合并楼盘城市名称
	private String wCityName;//待合并楼盘城市名称
	private String wDistrictId;//待合并楼盘区域名称
	private String wDistrictName;//待合并楼盘区域名称
	private String wAddress;//待合并楼盘地址
	private String wResource;//待合并楼盘来源
	private String wCaseNo;//待合并楼盘案例数
	
	/**
	 * 其他
	 */
	private Double nameScore;//楼盘名相似分
	private Double addressScore;//楼盘地址相似分
	private String isMerged;//是否合并，"1":是，"0"：否
	
	private Date createTime;//此数据创建时间
	
}
