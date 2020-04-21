package com.hifo.dataoperation.util;

import java.util.*;

/**
 * 常量
 *
 * @author whc
 * @date 2019/4/2 10:17
 */
public interface Constant {

    Map<String, String> NO = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            this.put("id", "0");
            this.put("value", "否");
        }
    });

    Map<String, String> YES = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            this.put("id", "1");
            this.put("value", "是");
        }
    });

    Map<String, String> PART = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            this.put("id", "2");
            this.put("value", "部分");
        }
    });

    List<Map<String, String>> BOOL_LIST = Collections.unmodifiableList(new ArrayList<Map<String, String>>() {
        {
            this.add(NO);
            this.add(YES);
        }
    });

    List<Map<String, String>> LIFT_LIST = Collections.unmodifiableList(new ArrayList<Map<String, String>>() {
        {
            this.add(NO);
            this.add(YES);
            this.add(PART);
        }
    });

    /**
     * 可以被导出的属性
     */
    Map<String, String> TITLE = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
        {
            this.put("id", "ID");
            this.put("cityName", "城市");
            this.put("districtName", "行政区");
            this.put("blockName", "片区板块");
            this.put("townName", "街道办/乡镇");
            this.put("name", "楼盘名");
            this.put("alias", "楼盘别名");
            this.put("buildingNo", "总栋数");
            this.put("roomNo", "总户数");
            this.put("buildingAcreage", "建筑面积");
            this.put("usedAcreage", "占地面积");
            this.put("capacity", "容积率");
            this.put("greenRatio", "绿化率");
            this.put("parkNo", "停车位数量");
            this.put("buildYearStart", "建成年代（起）");
            this.put("buildYearEnd", "建成年代（止）");
            this.put("location", "坐标（高德）");
            this.put("addrDetail", "详细地址");
            this.put("addrRef", "参考地址");
            this.put("developerDesc", "开发商");
            this.put("developerBrand", "开发商品牌");
            this.put("equipment", "物管设施");
            this.put("propertyRight", "产权性质");
            this.put("buildingCategory", "建筑类别");
            this.put("quality", "住宅品质");
            this.put("mainQuality", "主力品质");
            this.put("mainAcreage", "主力面积");
            this.put("mainCategory", "主力品质的建筑类别");
            this.put("priceDesc", "特殊说明");
        }
    });
        /**
         * 可以被导出楼栋的属性
         */
        Map<String, String> BUILDING = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
            {
                this.put("id", "ID");
                this.put("cityName","城市");
                this.put("districtName","行政区");
                this.put("estateName","楼盘名称");
                this.put("name", "楼栋名称");
                this.put("alias", "楼栋别名");
                this.put("unitNo", "单元数");
                this.put("floorOverGroundNo", "地上总楼层");
                this.put("floorUnderGroundNo", "地下总楼层");
                this.put("floorTotalNo", "总楼层");
                this.put("roomNoPerFloor", "标准层室号数");
                this.put("roomNo", "房号总数");
                this.put("builtDate", "竣工日期");
                this.put("liftNo", "电梯数");
                this.put("liftBrand", "电梯品牌");
                this.put("address", "详细地址");
                this.put("structure", "建筑结构");
                this.put("buildingType", "建筑类别");
                this.put("usedAcreage", "占地面积");
                this.put("quality", "住宅品质");
                this.put("avgPrice", "最新均价");
                this.put("priceCoe", "价格系数");
                this.put("wallDecoration", "价格系数");
                this.put("wallDecoration", "外墙装饰");
                this.put("publicDecoration", "公共装修档次");
                this.put("nonStandDesc", "非标准层说明");
                this.put("priceDesc", "价格差异说明");
                this.put("basePrice", "房号基准价");
                this.put("baseRoomNo", "房号基准价室号");
                this.put("haveLift", "有无电梯");
            }
    });
    /**
     * 可以被导出房号的属性
     */
    Map<String, String> ROOM = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
        {
            this.put("id", "ID");
            this.put("name", "房号名称");
            this.put("districtName", "行政区");
            this.put("blockName", "片区板块");
            this.put("townName", "街道办/乡镇");
            this.put("name", "楼盘名");
            this.put("alias", "楼盘别名");
            this.put("buildingNo", "总栋数");
            this.put("roomNo", "总户数");
            this.put("buildingAcreage", "建筑面积");
            this.put("usedAcreage", "占地面积");
            this.put("capacity", "容积率");
            this.put("greenRatio", "绿化率");
            this.put("parkNo", "停车位数量");
            this.put("buildYearStart", "建成年代（起）");
            this.put("buildYearEnd", "建成年代（止）");
            this.put("location", "坐标（高德）");
            this.put("addrDetail", "详细地址");
            this.put("addrRef", "参考地址");
            this.put("developerDesc", "开发商");
            this.put("developerBrand", "开发商品牌");
            this.put("equipment", "物管设施");
            this.put("propertyRight", "产权性质");
            this.put("buildingCategory", "建筑类别");
            this.put("quality", "住宅品质");
            this.put("mainQuality", "主力品质");
            this.put("mainAcreage", "主力面积");
            this.put("mainCategory", "主力品质的建筑类别");
            this.put("priceDesc", "特殊说明");
        }
    });

    /**
     * 可以被导出房号的属性
     */
    Map<String, String> AVGPRICE = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
        {
            this.put("id", "ID");
            this.put("estateName", "楼盘名称");
            this.put("cityName","城市");
            this.put("districtName", "行政区");
            this.put("blockName", "片区板块");
            this.put("standAcreage", "标准房面积");
            this.put("quality", "物业品质");
            this.put("buildingType", "建筑类别");
            this.put("calDate", "计算日期");
            this.put("monthOnMonth", "环比");
            this.put("caseNo", "计算案例");
            this.put("avgPriceSys", "系统均价");
            this.put("avgPriceCoe", "指数均价");
            this.put("avgPriceManual", "人工均价");

        }
    });
    String FALSE = "0";
    String TRUE = "1";

    // 可估
    String CAN_EVALUATE="1";
    String NOT_CAN_EVALUATE="0";

    // 五个系数的常量
    /**
     * 楼层系数
     */
    String FLOOR_COE = "1";
    /**
     * 朝向系数
     */
    String ORIENTATION_COE = "2";
    /**
     * 面积系数
     */
    String AREA_COE = "4";
    /**
     * 景观系数
     */
    String SCENERY_COE = "5";
    /**
     * 建筑结构系数
     */
    String STRUCTURE_COE = "3";


    // 是否可打印
    /**
     * 不可打印
     */
      String ALL_PEIENT = "1";
    /**
     * // 可打印1
     */
    String ALL_NOT_PEIENT = "0";
    /**
     * // 可古不可打印2
     */
    String CAN_EVA_PEIENT = "2";
}
