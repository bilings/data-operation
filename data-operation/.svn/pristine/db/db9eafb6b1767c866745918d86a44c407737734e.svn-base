package com.hifo.dataoperation.dao;



import com.hifo.dataoperation.entity.BusEstate;
import org.springframework.stereotype.Repository;

@Repository
public class BusEstateDao extends MongodbBasicDao<BusEstate> {

    @Override
    public Class<BusEstate> getEntityClass() {
        return BusEstate.class;
    }

    @Override
    public String getCollectionName() {
        return "warehouse_community_service";
    }
}
