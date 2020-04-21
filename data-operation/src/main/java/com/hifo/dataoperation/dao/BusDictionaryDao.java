package com.hifo.dataoperation.dao;



import com.hifo.dataoperation.entity.BusDictionary;
import org.springframework.stereotype.Repository;

@Repository
public class BusDictionaryDao extends MongodbBasicDao<BusDictionary> {

    @Override
    public Class<BusDictionary> getEntityClass() {
        return BusDictionary.class;
    }

    @Override
    public String getCollectionName() {
        return "bus_dictionary";
    }
}
