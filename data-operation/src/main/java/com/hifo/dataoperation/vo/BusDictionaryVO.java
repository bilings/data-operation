package com.hifo.dataoperation.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.entity.BusDictionary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * hf_dictionary vo
 *
 * @author jinzhichen
 * @date 2019/3/22 9:26
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("hf_dictionary")
public class BusDictionaryVO extends BusDictionary {

    @ApiModelProperty(value = "标题", name = "title")
    @TableField(exist = false)
    private String title;
}
