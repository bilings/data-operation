package com.hifo.dataoperation.entity.coe;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.entity.coe.floorCoeVO.hasLift;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 *@description  楼层系数 城市配置表
 *@author 柏小龙
 *@create 2020/3/30 18:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("floorCoefficient1")
public class FloorCoefficient extends BaseEntity {


    @ApiModelProperty(value="城市配置表ID",name="cid")
    private String cid;
    @ApiModelProperty(value="物业品质",name="quality")
    private String quality;
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;
    @ApiModelProperty(value="创建人",name="createUser")
    private String createUser;
    @ApiModelProperty(value="有电梯",name="hasLifts")
    private hasLift hasLifts;
    @ApiModelProperty(value="无电梯",name="noLifts")
    private hasLift noLifts;
}
