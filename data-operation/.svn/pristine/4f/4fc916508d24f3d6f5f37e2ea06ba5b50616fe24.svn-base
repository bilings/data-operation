package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * hf_operation_log
 *
 * @author whc
 * @date 2019/3/20 13:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("hf_operation_log")
public class HfOperationLog extends Entity {

    /**
     * 操作类型：新增
     */
    public static final String INSERT = "insert";
    /**
     * 操作类型：更新
     */
    public static final String UPDATE = "update";
    /**
     * 操作类型：删除
     */
    public static final String DELETE = "delete";

    private String tableName;

    private Long seqId;

    private String type;

    private String oldValue;

    private String newValue;

    private Integer operateId;

    private String operateName;

    private Date operateTime;
}
