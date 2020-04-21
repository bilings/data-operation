package com.hifo.dataoperation.entity;

import com.hifo.dataoperation.entity.mongo.Base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 字典
 *
 * @author whc
 * @date 2019/4/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
//@TableName("bus_dictionary")
@Document(collection ="bus_dictionary" )
public class BusDictionary extends BaseEntity {
    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 名称
     */
    private String item;
}