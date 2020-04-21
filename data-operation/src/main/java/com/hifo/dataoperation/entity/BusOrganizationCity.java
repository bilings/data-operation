package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xmw
 * @date 2019/05/08 18:06:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bus_organization_city")
public class BusOrganizationCity extends Entity {

    private Long organizationId;
    private Long administrativeId;
    private String mongoId;
    private String city;
    private Boolean isUse;
    private String regionId;
}
