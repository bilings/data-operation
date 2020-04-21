package com.hifo.dataoperation.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.entity.BusEstateAvgPrice;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *@description  
 *@author 张宗朋
 *@create  9:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bus_estate_avg_price")
public class BusEstateAvgPriceExtend extends BusEstateAvgPrice {

    @Excel(name = "楼盘名(必填)",orderNum="2")
    @TableField(exist = false)
    private String estateName;
    @TableField(exist = false)
    private String cityId;
    @Excel(name = "城市(必填)",orderNum="3")
    @TableField(exist = false)
    private String cityName;
    @Excel(name = "行政区(必填)",orderNum="4")
    @TableField(exist = false)
    private String districtName;
    @TableField(exist = false)
    private String districtId;
    @TableField(exist = false)
    private String blockName;
    @TableField(exist = false)
    private Integer caseNoMin;
    @TableField(exist = false)
    private Integer caseNoMax;
    @TableField(exist = false)
    private Float avgPriceSysMin;
    @TableField(exist = false)
    private Float avgPriceSysMax;
    @TableField(exist = false)
    private String calDateStart;
    @TableField(exist = false)
    private String calDateEnd;
    @TableField(exist = false)
    private String mainQuality;

}
