package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bus_setting
 *
 * @author jinzhichen
 * @date 2019/4/16 15;23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bus_setting")
public class BusSetting extends Entity {
    @ApiModelProperty(value = "客户分类id", name = "dictionaryId")
    private Integer dictionaryId;
    @ApiModelProperty(value = "机构id", name = "organizationId")
    private Integer organizationId;
    @ApiModelProperty(value = "城市id", name = "cityName")
    private String cityId;
    @ApiModelProperty(value = "城市名称", name = "cityName")
    private String cityName;
    @ApiModelProperty(value = "保留字段，0=住宅，1=商业", name = "scope")
    private String scope;
    @ApiModelProperty(value = "打印设置，0=不可打印，1=可打印，2=“可估”可打印'", name = "print")
    private String print;
    @ApiModelProperty(value = "询价单名称", name = "inquirySheetName")
    private String inquirySheetName;
    @ApiModelProperty(value = "机构类型", name = "organizationType")
    private String organizationType;
    @ApiModelProperty(value = "评估机构简称", name = "simplyName")
    private String simplyName;
    @ApiModelProperty(value = "估价说明", name = "evaluateDesc")
    private String evaluateDesc;
    @ApiModelProperty(value = "展示选填项", name = "showOptions")
    private String showOptions;
    @ApiModelProperty(value = "机构logo，保存路径", name = "logo")
    private String logo;
    @ApiModelProperty(value = "公司鲜章，保存路径", name = "chapter")
    private String chapter;
    @ApiModelProperty(value = "appid", name = "appid")
    private String appid;
    @ApiModelProperty(value = "secret", name = "secret")
    private String secret;
    @ApiModelProperty(value = "数据更新频率 1=每周，2=每月", name = "subscribeInterval")
    private String subscribeInterval;
    @ApiModelProperty(value = "订阅数据发布方式 1=自动审核，2=人工审核", name = "subscribeMethod")
    private String subscribeMethod;
    @ApiModelProperty(value = "更新人id", name = "updateId")
    private Integer updateId;
    @ApiModelProperty(value = "更信人名称", name = "updateName")
    private String updateName;
}
