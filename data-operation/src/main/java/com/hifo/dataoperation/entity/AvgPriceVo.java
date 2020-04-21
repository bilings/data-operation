package com.hifo.dataoperation.entity;

import lombok.Data;

import java.util.Date;

/**
 *@description  页面参数实体类
 *@author 张宗朋
 *@create 2019/5/27 11:00
 */
@Data
public class AvgPriceVo {

    private Long userId;

    /**
     * 机构id
     */
    private Long orgId;
    /**
     * 机构名称
     */
    private String  orgName;
    /**
     * logo地址
     */
    private String logoPath;
    /**
     * 电子章地址
     */
    private String chapterPath;
    /**
     * 楼盘id
     */
    private Long estateId;
    /**
     * 楼盘名称
     */
    private String estateName;
    /**
     * 楼盘地址
     */
    private String address;
    /**
     * 住宅品质（必填-高层、洋房、别墅）
     */
    private Long quality;
    /**
     * 主力面积
     */
    private Float mainArea;
    /**
     * 建筑面积 (必填)
     */
    private Float buildArea;
    /**
     * 楼栋Id
     */
    private Long buildingId;
    /**
     * 楼栋名称
     */
    private String buildingName;
    /**
     * 单元
     */
    private String unit;
    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 总楼层
     */
    private Integer floorTotalNo;
    /**
     * 房号
     */
    private String roomName;
    /**
     * 房号Id
     */
    private Long roomId;
    /**
     *有无电梯  数据字典Id
     */
    private Integer  haveLift;
    /**
     *朝向(数据字典Id)
     */
    private Long orientation;
    /**
     *景观(数据字典Id)
     */
    private Long scenery;
    /**
     *建筑结构(数据字典Id)
     */
    private Long structure;
    /**
     *建筑类别(数据字典Id)
     */
    private Long buildingCategory;
    /**
     *建筑年代
     */
    private Integer buildYear;

    /**
     *建筑年代
     */ private Date createTime;

    /**
     *均价
     */
    private Float avgPrice;

    /**
     *算出房子单价（一房一价）
     */
    private Float askPrice;

    /**
     *总价
     */
    private Float totalPrice;

    /**
     *是否可打印
     */
    private Boolean print;

    /**
     *描述
     */
    private String  description;

    /**
     * 可估描述
     */
    private String  canDescription;

    /**
     * 估价说明
     */
    private String  canPriceDescription;
    /**
     * 标准房面积
     */
    private  Float standAcreage;

}
