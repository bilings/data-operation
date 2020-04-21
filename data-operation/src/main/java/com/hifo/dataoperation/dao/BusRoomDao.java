package com.hifo.dataoperation.dao;

import com.hifo.dataoperation.entity.BusRoom;
import org.springframework.stereotype.Repository;

@Repository
public class BusRoomDao extends MongodbBasicDao<BusRoom> {
    @Override
    public Class<BusRoom> getEntityClass() {
        return BusRoom.class;
    }

    @Override
    public String getCollectionName() {
        return "warehouse_room_service";
    }
}
