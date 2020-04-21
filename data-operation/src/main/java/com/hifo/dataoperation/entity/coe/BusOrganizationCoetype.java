package com.hifo.dataoperation.entity.coe;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author 杨捷
 * @since 2019-05-24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bus_organization_coetype")
public class BusOrganizationCoetype extends Entity {

    @TableField("organization_Id")
    private Integer organizationId;
    private String coetypes;
    @TableField("create_By")
    private String createBy;
    @TableField("create_Time")
    private Date createTime;
    @TableField("administrative_id")
    private String administrativeId;

}
