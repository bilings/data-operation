package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 虚拟日志
 *
 * @author whc
 * @date 2019/4/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
//@TableName("bus_virtual_rel")
@Document(collection = "bus_virtual_rel")
public class BusVirtualRel extends BaseEntity {

    private String fromId;

    private String toId;

    private String createName;

    private Date createTime;

}