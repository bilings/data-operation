package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.BusVirtualRel;
import org.springframework.stereotype.Repository;

@Repository
public class BusVirtualRelDao extends MongodbBasicDao<BusVirtualRel> {

    @Override
    public Class<BusVirtualRel> getEntityClass() {
        return BusVirtualRel.class;
    }

    @Override
    public String getCollectionName() {
        return "bus_virtual_rel";
    }
}
