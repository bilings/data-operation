package com.hifo.dataoperation.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 张宗朋
 * @description
 * @create 16:19
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("bus_estate_avg_price")
public class BusEstateAvgPrice {

    private static final long serialVersionUID = 1L;

    @Excel(name = "ID",orderNum="1")
    private Long id;
    private String esId;
    private Integer organizationId;
    private Long estateId;
    @Excel( name = "物业品质(必填)",orderNum="5", replace = {"普通住宅（公寓）_10", "高档公寓_11", "花园洋房_12", "联排别墅_13", "叠拼别墅_14", "双拼别墅_15", "独立别墅_16", "类独栋_17", "别墅_18"})
    private String quality;
    @Excel( name = "建筑类别",orderNum="6", replace = {"低层_19", "多层_20", "小高层_21", "高层_22", "超高层_23"})
    private String buildingType;
    @Excel( name = "标准房面积",orderNum="7")
    private Float standAcreage;
    @Excel( name = "日期(必填：yyyyMM)",orderNum="8")
    private String calDate;
    @Excel( name = "涨幅",orderNum="9")
    private String monthOnMonth;
    @Excel( name = "案例数",orderNum="10")
    private Integer caseNo;
    @Excel( name = "系统均价",orderNum="11")
    private Float avgPriceSys;
    @Excel( name = "指数均价",orderNum="12")
    private Float avgPriceCoe;
    @Excel( name = "人工均价",orderNum="13")
    private Float avgPriceManual;
    @Excel( name = "是否审核",orderNum="14", replace = {"已审核_1", "未审核_0"})
    private String isAudit;
    private String visibility;
}
