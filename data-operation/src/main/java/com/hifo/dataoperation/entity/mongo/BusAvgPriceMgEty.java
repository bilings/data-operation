package com.hifo.dataoperation.entity.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼盘均价数据操作对象
 * @author weisiibn
 * @date 2020年4月8日15:53:42
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Document(collection = "warehouse_community_price1")
public class BusAvgPriceMgEty extends BaseEntity{
	private static final long serialVersionUID = -7093876827934225414L;
	@ApiModelProperty(value = "城市id", name = "cityId")
	private String cityId;//城市id
	@ApiModelProperty(value = "城市名", name = "cityName")
	private String cityName;//城市名
	@ApiModelProperty(value = "区域id", name = "districtId")
	private String districtId;//区域id
	@ApiModelProperty(value = "区域名", name = "districtName")
	private String districtName;//区域名
	@ApiModelProperty(value = "楼盘id", name = "communityId")
    private String communityId;//楼盘id
    @ApiModelProperty(value = "楼盘名称", name = "communityName")
    private String communityName;
    //@Excel( name = "物业品质(必填)",orderNum="5", replace = {"普通住宅（公寓）_10", "高档公寓_11", "花园洋房_12", "联排别墅_13", "叠拼别墅_14", "双拼别墅_15", "独立别墅_16", "类独栋_17", "别墅_18"})
    @ApiModelProperty(value = "物业品质(必填)类型", name = "propertyQualityType")
    private String propertyQualityType;//物业品质类型
    @ApiModelProperty(value = "物业品质(必填)类型名", name = "propertyQualityTypeName")
    private String propertyQualityTypeName;//物业品质类型名
    //@Excel( name = "建筑类别",orderNum="6", replace = {"低层_19", "多层_20", "小高层_21", "高层_22", "超高层_23"})
    @ApiModelProperty(value = "建筑类别", name = "buildingType")
    private String buildingType;
    @ApiModelProperty(value = "建筑类别名", name = "buildingTypeName")
    private String buildingTypeName;//建筑类型名
    @ApiModelProperty(value = "标准房面积", name = "standAcreage")
    private Float standAcreage;
    @ApiModelProperty(value = "面积段", name = "areasSection")
    private List<String> areasSection;//面积段
    @ApiModelProperty(value = "价格日期(必填：yyyyMM)", name = "calDate")
    private String calDate;
    @ApiModelProperty(value = "月环比", name = "monthGain")
    private String monthGain;//月环比
    @ApiModelProperty(value = "案例数", name = "caseNo")
    private Integer caseNo;
    @ApiModelProperty(value = "系统均价", name = "avgPriceSys")
    private Float avgPriceSys;
    @ApiModelProperty(value = "指数均价", name = "avgPriceCoe")
    private Float avgPriceCoe;
    @ApiModelProperty(value = "人工均价", name = "avgPriceManual")
    private Float avgPriceManual;
    @ApiModelProperty(value = "是否审核(已审核_1, 未审核_0)" , name = "isAudit" )
    private String isAudit;
    @ApiModelProperty(value = "是否可见(是_1, 否_0)" , name = "visibility" )
    private String visibility;
    @ApiModelProperty(value = "已用案例的id集合" , name = "usedCaseIdList" )
    private List<String> usedCaseIdList;
    @ApiModelProperty(value = "是否修正了系统均价(是_1, 否_0)" , name = "isEditPriceAvgPrice" )
    private String isEditPriceAvgPrice;
}
