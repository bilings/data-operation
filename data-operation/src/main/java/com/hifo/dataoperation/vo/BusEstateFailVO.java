package com.hifo.dataoperation.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼盘导入失败VO
 *
 * @author xmw
 * @date 2019/05/24 13:06:05
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class BusEstateFailVO {

    @Excel(name = "ID")
    private Long id;

    @Excel(name = "楼盘名(必填)")
    private String name;

    @Excel(name = "楼盘别名")
    private String alias;

    @Excel(name = "城市(必填)")
    private String cityName;

    @Excel(name = "行政区(必填)")
    private String districtName;

    @Excel(name = "片区板块")
    private String blockName;

    @Excel(name = "街道办/乡镇")
    private String townName;

    @Excel(name = "总栋数")
    private Integer totalBuildingNo;

    @Excel(name = "已建栋数")
    private Integer buildingNo;

    @Excel(name = "总户数")
    private Integer totalRoomNo;

    @Excel(name = "已建户数")
    private Integer roomNo;

    @Excel(name = "建筑面积")
    private Double totalArea;

    @Excel(name = "占地面积")
    private Double landArea;

    @Excel(name = "容积率")
    private Double capacity;

    @Excel(name = "绿化率")
    private Double greenRatio;

    @Excel(name = "停车位数量")
    private Integer parkNo;

    @Excel(name = "建成年代（起）")
    private String buildYearStart;

    @Excel(name = "建成年代（止）")
    private String buildYearEnd;

    @Excel(name = "详细地址")
    private String addrDetail;

    @Excel(name = "参考地址")
    private String addrRef;

    @Excel(name = "开发商")
    private String developerMaintain;

    @Excel(name = "开发商品牌")
    private String developerBrand;

    @Excel(name = "物管设施")
    private String equipment;

    @Excel(name = "产权性质", replace = {"商品房_1", "经济适用房_2", "房改房_3", "集资房_4", "自建房_5", "还迁安置房_6", "公租房_7", "其他_8"})
    private String propertyRight;

    @Excel(name = "建筑类别")
    private String buildingCategory;

    @Excel(name = "住宅品质")
    private String quality;

    @Excel(name = "主力品质", replace = {"普通住宅（公寓）_10", "高档公寓_11", "花园洋房_12", "联排别墅_13", "叠拼别墅_14", "双拼别墅_15", "独立别墅_16", "类独栋_17", "别墅_18"})
    private String mainQuality;

    @Excel(name = "主力面积")
    private Double mainArea;

    @Excel(name = "主力品质的建筑类别", replace = {"低层_19", "多层_20", "小高层_21", "高层_22", "超高层_23"})
    private String mainCategory;

    @Excel(name = "特殊说明")
    private String priceDesc;

    @Excel(name = "失败原因")
    private String reason;

}
