package com.hifo.dataoperation.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * ext_user
 *
 * @author whc
 * @date 2019/3/14 8:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ext_employee")
public class ExtEmployee extends Entity {

    private Integer organizationId;

    private String name;

    private String username;

    private String password;

    private String tel;

    private String moduleCodes;

    private String createTime;

    private String[] citys;
}
