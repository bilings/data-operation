package com.hifo.dataoperation.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *@description  
 *@author 张宗朋
 *@create  9:18
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BusEstateAvgPriceVO {

    @Excel(name = "ID")
    private Long id;
    @Excel(name = "楼盘名(必填)")
    private String estateName;
    @Excel(name = "城市(必填)")
    private String cityName;
    @Excel(name = "行政区(必填)")
    private String districtName;
    private String blockName;
    private String esId;
    private Integer organizationId;
    private Long estateId;
    @Excel( name = "物业品质(必填)", replace = {"普通住宅（公寓）_10", "高档公寓_11", "花园洋房_12", "联排别墅_13", "叠拼别墅_14", "双拼别墅_15", "独立别墅_16", "类独栋_17", "别墅_18"})
    private String quality;
    @Excel( name = "建筑类别", replace = {"低层_19", "多层_20", "小高层_21", "高层_22", "超高层_23"})
    private String buildingType;
    @Excel( name = "标准房面积")
    private Float standAcreage;
    @Excel( name = "日期(必填：yyyyMM)")
    private String calDate;
    @Excel( name = "涨幅")
    private String monthOnMonth;
    @Excel( name = "案例数")
    private Integer caseNo;
    @Excel( name = "系统均价")
    private Float avgPriceSys;
    @Excel( name = "指数均价")
    private Float avgPriceCoe;
    @Excel( name = "人工均价")
    private Float avgPriceManual;
    @Excel( name = "是否审核", replace = {"已审核_1", "未审核_0"})
    private String isAudit;
    @Excel(name = "失败原因")
    private String reason;

}
