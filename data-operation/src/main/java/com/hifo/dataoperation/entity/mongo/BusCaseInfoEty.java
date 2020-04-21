package com.hifo.dataoperation.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection = "warehouse_caseinfo_standard")
public class BusCaseInfoEty extends BaseEntity{

	private static final long serialVersionUID = -1632743489613909645L;
	
	private String name;//案例楼盘名称
	private String louDong;//楼栋
	private String floor;//楼层
	private String totalFloor;//总楼层
	private Double buildArea;//建筑面积
	private String roomType;//户型
	private String directionType;//朝向
	private String scenery;//景观
	private String caseDate;//案例时间
	private String siteName;//来源
	private String communityUrl;//楼盘信息url
	
}
