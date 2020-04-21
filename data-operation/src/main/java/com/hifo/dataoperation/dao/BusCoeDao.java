package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.coe.FloorCityConfig;
import org.springframework.stereotype.Repository;

@Repository
public class BusCoeDao extends MongodbBasicDao<FloorCityConfig> {
    @Override
    public Class<FloorCityConfig> getEntityClass() {
        return FloorCityConfig.class;
    }

    @Override
    public String getCollectionName() {
        return "floorCityConfig";
    }
}
