package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * module
 *
 * @author xmw
 * @date 2019/05/10 11:40:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("module")
public class Module extends Entity {

    @ApiModelProperty(value = "模块code", name = "code")
    private String code;
    @ApiModelProperty(value = "模块名", name = "name")
    private String name;
    @ApiModelProperty(value = "父级模块id", name = "parentId")
    private Integer parentId;

}
