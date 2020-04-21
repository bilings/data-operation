package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bus_room
 *
 * @author bxl
 * @date 2020/4/08 15;43
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("warehouse_room_service")
public class BusRoom extends BaseEntity {

    @ApiModelProperty(value = "楼栋id", name = "communityId")
    private String communityId;
    @ApiModelProperty(value = "楼盘名称", name = "communityName")
    private String communityName;
    @ApiModelProperty(value = "楼栋id", name = "buildingId")
    private String buildingId;
    @Excel(name = "单元名称")
    @ApiModelProperty(value = "单元名称", name = "unit")
    private String unit;
    @Excel(name = "房号")
    @ApiModelProperty(value = "房号", name = "roomNo")
    private String roomNo;
    @Excel(name = "建筑面积")
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "建筑面积", name = "buildArea")
    private Float buildArea;
    @Excel(name = "套内面积")
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "套内面积", name = "innerArea")
    private Float innerArea;
    @Excel(name = "朝向", replace = {"东_36", "东北_40", "东南_41", "东西_45", "北_39", "南_37", "南北_44", "西_38", "西北_43", "西南_42"})
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "朝向", name = "orientation")
    private String orientation;
    @Excel(name = "景观", replace = {"中庭_51", "主干道_53", "公园_48", "堡砍_55", "山景_50", "快速干道_54", "支路_52", "江景_46", "海景_49", "湖景_47", "铁轨_57", "高架桥_56"})
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "景观", name = "scenery")
    private String scenery;
    @Excel(name = "户型结构", replace = {"其他_266", "复式楼_264", "平层_262", "跃层_265", "错层_263"})
    @TableField(strategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "户型结构", name = "structure")
    private String structure;
    @ApiModelProperty(value = "户型", name = "houseType")
    private String houseType;
    @ApiModelProperty(value = "初始价格", name = "initPrice")
    @TableField(strategy = FieldStrategy.IGNORED)
    private Float initPrice;
    @Excel(name = "价格系数")
    @ApiModelProperty(value = "价格系数", name = "priceCoe")
    @TableField(strategy = FieldStrategy.IGNORED)
    private Float priceCoe;
    @Excel(name = "物理层")
    @ApiModelProperty(value = "物理层", name = "physicsFloor")
    private Integer physicsFloor;
    @ApiModelProperty(value = "第几室", name = "roomOrder")
    private Integer roomOrder;
    @Excel(name = "逻辑层")
    @ApiModelProperty(value = "名义层", name = "nominalFloor")
    private String nominalFloor;
    @ApiModelProperty(value = "所在层", name = "floor")
    private String floor;
    @ApiModelProperty(value = "创建人", name = "createId")
    private String createId;
    @ApiModelProperty(value = "创建人名称", name = "createName")
    private String createName;
    @ApiModelProperty(value = "上次修改时间", name = "createTime")
    private java.util.Date createTime;
    @ApiModelProperty(value = "上次修改人", name = "updateId")
    private String updateId;
    @ApiModelProperty(value = "修改人名称", name = "updateName")
    private String updateName;
    @ApiModelProperty(value = "上次修改时间", name = "updateTime")
    private java.util.Date updateTime;
}