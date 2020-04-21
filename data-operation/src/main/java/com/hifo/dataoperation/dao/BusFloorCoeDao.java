package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.coe.FloorCityConfig;
import com.hifo.dataoperation.entity.coe.FloorCoefficient;
import org.springframework.stereotype.Repository;

@Repository
public class BusFloorCoeDao extends MongodbBasicDao<FloorCoefficient> {
    @Override
    public Class<FloorCoefficient> getEntityClass() {
        return FloorCoefficient.class;
    }

    @Override
    public String getCollectionName() {
        return "floorCoefficient1";
    }
}
