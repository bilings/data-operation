package com.hifo.dataoperation.dao;



import com.hifo.dataoperation.entity.BusCategory;
import com.hifo.dataoperation.entity.BusDictionary;
import org.springframework.stereotype.Repository;

@Repository
public class BusCategoryDao extends MongodbBasicDao<BusCategory> {

    @Override
    public Class<BusCategory> getEntityClass() {
        return BusCategory.class;
    }

    @Override
    public String getCollectionName() {
        return "bus_category";
    }
}
