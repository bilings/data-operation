package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * 客戶机构
 *
 * @author xmw
 * @date 2019/5/5 16;12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ext_customer_org")
public class CustomerOrg extends Entity {

    @ApiModelProperty(value = "评估机构id", name = "organizationId")
    private Integer organizationId;

    @ApiModelProperty(value = "机构名称", name = "name")
    private String name;

    @ApiModelProperty(value = "机构类型", name = "type")
    private Integer type;

    @ApiModelProperty(value = "剩余次数", name = "times")
    private Integer times;

    @ApiModelProperty(value = "机构层级", name = "rank")
    private String rank;

    @ApiModelProperty(value = "上级机构id", name = "parentId")
    private Long parentId;

    @ApiModelProperty(value = "是否删除", name = "isDelete")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建人id", name = "createId")
    private Integer createId;

    @ApiModelProperty(value = "创建人", name = "createName")
    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改人id", name = "updateId")
    private Integer updateId;

    @ApiModelProperty(value = "修改人", name = "updateName")
    private String updateName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间", name = "updateTime")
    private Timestamp updateTime;

}
