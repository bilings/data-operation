package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 合并日志
 *
 * @author whc
 * @date 2019/4/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
//@TableName("bus_merge_rel")
@Document(collection = "bus_merge_rel")
public class BusMergeRel extends BaseEntity {

//    private Integer organizationId;

//    private String fromEsId;
//
//    private String toEsId;

    private String fromId;

    private String  toId;

//    private Integer createId;

    private String createName;

    private Date createTime;
}