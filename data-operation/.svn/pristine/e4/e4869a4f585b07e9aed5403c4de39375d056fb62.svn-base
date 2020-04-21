package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ext_organization
 *
 * @author whc
 * @date 2019/3/22 9:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ext_organization")
public class ExtOrganization extends Entity {
    @ApiModelProperty(value = "机构名称", name = "name")
    private String name;
    @ApiModelProperty(value = "机构介绍", name = "introduction")
    private String introduction;
    @ApiModelProperty(value = "机构类型", name = "type")
    private String type;
    @ApiModelProperty(value = "简称", name = "abbreviation")
    private String abbreviation;
    @ApiModelProperty(value = "使用次数", name = "times")
    private String times;
    @ApiModelProperty(value = "创建时间", name = "name")
    private String createTime;
    @ApiModelProperty(value = "数据更新频率", name = "subscribeInterval")
    private Integer subscribeInterval;
    @ApiModelProperty(value = "订阅数据发布方式", name = "subscribeMethod")
    private Integer subscribeMethod;
}
