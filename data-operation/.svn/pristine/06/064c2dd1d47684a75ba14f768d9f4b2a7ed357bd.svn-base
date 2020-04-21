package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bus_estate_agent
 *
 * @author xmw
 * @date 2019/05/24 14:40:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bus_estate_agent")
public class BusEstateAgent extends Entity {

    @ApiModelProperty(value = "预合并楼盘id", name = "fromId")
    private Long fromId;

    @ApiModelProperty(value = "合并至楼盘id", name = "toId")
    private Long toId;

    @ApiModelProperty(value = "评估机构id", name = "organizationId")
    private Integer organizationId;

    @ApiModelProperty(value = "名称推荐分", name = "nameScore")
    private Double nameScore;

    @ApiModelProperty(value = "地址推荐分", name = "addrScore")
    private Double addrScore;

    @ApiModelProperty(value = "坐标推荐分", name = "coordinateScore")
    private Double coordinateScore;

    @ApiModelProperty(value = "是否推荐 0不推荐 1推荐 （默认1推荐）", name = "isRecommend")
    private Boolean isRecommend;

    @ApiModelProperty(value = "是否删除", name = "isDelete")
    private Boolean isDelete;

    @ApiModelProperty(value = "最近创建时间", name = "createTime")
    private String createTime;

}
