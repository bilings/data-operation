package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.BusMergeRel;
import com.hifo.dataoperation.entity.BusVirtualRel;
import org.springframework.stereotype.Repository;

@Repository
public class BusMergeRelDao extends MongodbBasicDao<BusMergeRel> {

    @Override
    public Class<BusMergeRel> getEntityClass() {
        return BusMergeRel.class;
    }

    @Override
    public String getCollectionName() {
        return "bus_merge_rel";
    }
}
