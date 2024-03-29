package com.hifo.dataoperation.dao;


import com.hifo.dataoperation.entity.mongo.BuiltEstate;
import org.springframework.stereotype.Repository;

@Repository
public class BuiltEstateDao extends MongodbBasicDao<BuiltEstate> {

    @Override
    public Class<BuiltEstate> getEntityClass() {
        return BuiltEstate.class;
    }

    @Override
    public String getCollectionName() {
        return "warehouse_community_standard";
    }
}
