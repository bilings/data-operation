package com.hifo.dataoperation.entity.mongo.Base;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
//    @Id
    String id;
}
