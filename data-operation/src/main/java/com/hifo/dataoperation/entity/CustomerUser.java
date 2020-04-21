package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * 客户信息
 *
 * @author xmw
 * @date 2019/05/05 16:34:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ext_customer_user")
public class CustomerUser extends Entity {

    @ApiModelProperty(value = "评估机构id", name = "organizationId")
    private Integer organizationId;

    @ApiModelProperty(value = "账户（支持手机号）", name = "username")
    private String username;

    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    @ApiModelProperty(value = "客户姓名", name = "name")
    private String name;

    @ApiModelProperty(value = "机构类型", name = "type")
    private Integer type;

    @ApiModelProperty(value = "联系方式", name = "tel")
    private Long tel;

    @ApiModelProperty(value = "客户机构id", name = "orgId")
    @TableField(value = "orgId", strategy = FieldStrategy.IGNORED)
    private Long orgId;

    @ApiModelProperty(value = "openid", name = "openid")
    @TableField(value = "openid")
    private String openid;

    @ApiModelProperty(value = "客户机构名称", name = "orgName")
    @TableField(value = "orgName", strategy = FieldStrategy.IGNORED)
    private String orgName;

    @ApiModelProperty(value = "机构层级", name = "rank")
    @TableField(value = "rank", strategy = FieldStrategy.IGNORED)
    private String rank;

    @ApiModelProperty(value = "剩余次数", name = "times")
    private Integer times;

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

    @ApiModelProperty(value = "appid", name = "appid")
    @TableField(exist = false)
    private String appid;

    @ApiModelProperty(value = "code", name = "code")
    @TableField(exist = false)
    private String code;

    @ApiModelProperty(value = "secret", name = "secret")
    @TableField(exist = false)
    private String secret;



}
