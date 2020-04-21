package com.hifo.dataoperation.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bus_room
 *
 * @author jinzhichen
 * @date 2019/4/16 15;23
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BusBuildingFailVO {

    @Excel(name = "ID")
    private String id;
    @Excel(name = "城市(必填)")
    private String cityName;
    @Excel(name = "行政区(必填)")
    private String districtName;
    @Excel(name = "楼盘名(必填)")
    private String estateName;
    @Excel(name = "楼栋名称(必填)")
    private String name;
    @Excel(name = "楼栋别名")
    private String alias;
    @Excel(name = "单元数")
    private Integer unitNo;
    @Excel(name = "单元名称集合,用逗号隔开")
    private String unitName;
    @Excel(name = "地上层数")
    private Integer floorOverGroundNo;
    @Excel(name = "地下层数")
    private Integer floorUnderGroundNo;
    @Excel(name = "总楼层")
    private Integer floorTotalNo;
    @Excel(name = "标准层室号数")
    private Integer roomNoPerFloor;
    @Excel(name = "房号总数")
    private Integer roomNo;
    @Excel(name = "竣工日期")
    private String builtDate;
    @Excel(name = "电梯数")
    private Integer liftNo;
    @Excel(name = "电梯品牌")
    private String liftBrand;
    @Excel(name = "详细地址")
    private String address;
    @Excel(name = "建筑结构", replace = {"钢混结构_223", "砖混结构_224", "钢结构_225", "砖木结构_226", "其他结构_227", "钢筋混凝土结构_232", "钢和钢筋混凝土结构_233", "混合结构_236"})
    private String structure;
    @Excel(name = "建筑类别", replace = {"低层_19", "多层_20", "小高层_21", "高层_22", "超高层_23"})
    private String buildingType;
    @Excel(name = "占地面积")
    private Float landArea;
    @Excel(name = "住宅品质", replace = {"普通住宅（公寓）_10", "高档公寓_11", "花园洋房_12", "联排别墅_13", "叠拼别墅_14", "双拼别墅_15", "独立别墅_16", "类独栋_17", "别墅_18"})
    private String quality;
    @Excel(name = "最新均价")
    private Float avgPrice;
    @Excel(name = "价格系数")
    private Float priceCoe;
    @Excel(name = "外墙装饰", replace = {"外墙漆涂料_241", "面砖_242", "马赛克_243", "石材_244", "玻璃幕墙_245", "铝塑板_246", "合金饰面板_247", "文化石_248", "抹灰_249", "清水_250"})
    private String wallDecoration;
    @Excel(name = "公共装修档次", replace = {"简装_61", "普通_62", "中档_63", "高档_64", "豪华_65"})
    private String publicDecoration;
    @Excel(name = "非标准层说明")
    private String nonStandDesc;
    @Excel(name = "价格差异说明")
    private String priceDesc;
    @Excel(name = "房号连接符")
    private String connector;
    @Excel(name = "房号基准价")
    private Float basePrice;
    @Excel(name = "房号基准价室号")
    private String baseRoomNo;
    @Excel(name = "经度（高德）")
    private Float lngGd;
    @Excel(name = "纬度（高德）")
    private Float latGd;
    @Excel(name = "经度（百度）")
    private Float lngBd;
    @Excel(name = "纬度（百度）")
    private Float latBd;
    @Excel(name = "有无电梯", replace = {"有电梯_273", "无电梯_274", "部分电梯_275"})
    private Integer haveLift;
    @Excel(name = "是否可估", replace = {"可估_1", "不可估_0"})
    private String canEvaluate;
    @Excel(name = "是否完善", replace = {"完善_1", "不完善_0"})
    private String isPerfect;
    @Excel(name = "失败原因")
    private String reason;
}