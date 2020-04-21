package com.hifo.dataoperation.entity.coe;

import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *@description  楼层系数 城市配置表
 *@author 柏小龙
 *@create 2020/3/30 18:20
 */
@Data
@Document(collection = "floorCityConfig")
public class FloorCityConfig extends BaseEntity {

    @ApiModelProperty(value="上级ID",name="pid")
    private String pid;
    @ApiModelProperty(value="0：通用系数，1：城市系数",name="type")
    private String type;
    @ApiModelProperty(value="城市ID",name="cityId")
    private String cityId;
    @ApiModelProperty(value="城市名称",name="cityName")
    private String cityName;
    @ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;
    @ApiModelProperty(value="创建人",name="createUser")
    private String createUser;
}
