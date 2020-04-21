package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.BusBuilding;
import org.springframework.stereotype.Repository;

@Repository
public class BusBuildingDao extends MongodbBasicDao<BusBuilding> {

    @Override
    public Class<BusBuilding> getEntityClass() {
        return BusBuilding.class;
    }

    @Override
    public String getCollectionName() {
        return "building";
    }
}