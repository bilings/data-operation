package com.hifo.dataoperation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.entity.BusEstateAgent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 推荐合并楼盘VO
 *
 * @Author: xmw
 * @Date: 2019/5/24 14:50
 */
@Data
@TableName("bus_estate_agent")
public class BusEstateAgentVO extends BusEstateAgent {

    @ApiModelProperty(value = "城市id", name = "cityId")
    @TableField(exist = false)
    private String cityId;

    @ApiModelProperty(value = "预合并楼盘名称", name = "fromName")
    @TableField(exist = false)
    private String fromName;

    @ApiModelProperty(value = "预合并楼盘案例数", name = "fromCaseNo")
    @TableField(exist = false)
    private String fromCaseNo;

    @ApiModelProperty(value = "预合并楼盘行政区id", name = "fromDistrictId")
    @TableField(exist = false)
    private String fromDistrictId;

    @ApiModelProperty(value = "预合并楼盘行政区", name = "fromDistrictName")
    @TableField(exist = false)
    private String fromDistrictName;

    @ApiModelProperty(value = "预合并楼盘地址", name = "fromAddr")
    @TableField(exist = false)
    private String fromAddr;

    @ApiModelProperty(value = "总栋数", name = "totalBuildingNo")
    @TableField(exist = false)
    private Integer totalBuildingNo;

    @ApiModelProperty(value = "总户数", name = "totalRoomNo")
    @TableField(exist = false)
    private Integer totalRoomNo;

    @ApiModelProperty(value = "合并至楼盘名称", name = "toName")
    @TableField(exist = false)
    private String toName;

    @ApiModelProperty(value = "合并至楼盘案例数", name = "toCaseNo")
    @TableField(exist = false)
    private String toCaseNo;

    @ApiModelProperty(value = "合并至楼盘行政区", name = "toDistrictName")
    @TableField(exist = false)
    private String toDistrictName;

    @ApiModelProperty(value = "合并至楼盘地址", name = "toAddr")
    @TableField(exist = false)
    private String toAddr;

    @ApiModelProperty(value = "是否代建楼盘，0=否，1=是", name = "isAgent")
    @TableField(exist = false)
    private Boolean isAgent;

}
