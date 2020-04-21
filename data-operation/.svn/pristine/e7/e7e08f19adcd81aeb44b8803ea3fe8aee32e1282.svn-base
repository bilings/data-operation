package com.hifo.dataoperation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hifo.dataoperation.base.Entity;
import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
//@TableName("bus_administrative")
@Document(collection = "administrativeDivision")
public class BusAdministrative extends BaseEntity {
    private String regionId;
    private String name;
    private String type;
    private String simpleName;
    private String pinyin;
    private String pinyinCode;
    private String parentId;
    private String parentName;
    private String otherName;
    private String[] position;
    @Transient
    private String mongoId;
}
