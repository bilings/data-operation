package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * hf_sequence
 *
 * @author xmw
 * @date 2019/05/10 11:40:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hf_sequence")
public class HfSequence extends Entity {

    @ApiModelProperty(value = "名称", name = "seqName")
    private String seqName;
    @ApiModelProperty(value = "步长", name = "seqIncrement")
    private Integer seqIncrement;
    @ApiModelProperty(value = "初始值", name = "seqStart")
    private Integer seqStart;
    @ApiModelProperty(value = "当前值", name = "seqCurr")
    private Integer seqCurr;

}
