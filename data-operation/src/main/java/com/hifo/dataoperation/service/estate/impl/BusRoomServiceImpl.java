package com.hifo.dataoperation.service.estate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusRoomDao;
import com.hifo.dataoperation.entity.BusBuilding;
import com.hifo.dataoperation.entity.BusRoom;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusRoomService;
import com.hifo.dataoperation.util.StringUtil;
import com.hifo.dataoperation.util.TableEnum;
import lombok.var;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 房号
 *
 * @author jinzhichen
 * @date 2019/4/23 15:22
 */

@Service
public class BusRoomServiceImpl implements BusRoomService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BusRoomDao busRoomDao;
    @Autowired
    private BasicService basicService;

    @Override
    public ApiResult queryList(String buildingId,String util) {


        Query query=new Query();
        Criteria criteria=new Criteria();
        criteria.and("buildingId").is(buildingId);
        criteria.and("unit").is(util);
        query.addCriteria(criteria);
        var roomList=busRoomDao.selectByQuery(query,BusRoom.class);


        //FindIterable<Document> roomList =  mongoTemplate.getCollection("warehouse_room_service").find(document);
        /*List<Map<String,List<Object>>> listRoom = new ArrayList<>();
        Map<String,List<Object>> mapRooms = new HashMap<>();
        for(Document room:roomList){
            int physicsFloor = room.getInteger("physicsFloor");
            if(mapRooms.containsKey(physicsFloor+"")){
                List<Object> o= (List<Object>) mapRooms.get(physicsFloor+"");
                o.add((Map)room);
            }else {
                List<Object> o= new ArrayList<>();
                o.add((Map)room);
                mapRooms.put(physicsFloor+"",o);
            }
        }
        for (String s : mapRooms.keySet()) {
            Map map = new HashMap();
            map.put("roomSize",mapRooms.get(s).size());
            map.put("key",s);
            Collections.sort(mapRooms.get(s),new Comparator<Object>(){
                @Override
                public int compare(Object o1, Object o2) {
                    Document document1 = (Document) o1;
                    Document document2 = (Document) o2;
                    if(document1.getInteger("room")-document2.getInteger("room")>0){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });
            System.out.println(mapRooms.get(s));
            map.put(s,mapRooms.get(s));
            listRoom.add(map);
        }*/
        return ApiResult.success(roomList);
    }

    @Override
    public List<BusRoom> list(QueryWrapper<BusRoom> queryWrapper) {
        return null;
    }

    @Override
    public void save(BusRoom room,String lay) {

            String userId=basicService.getLoginEmployee().getId().toString();
            String userName=basicService.getLoginEmployee().getName();
            if (StringUtil.isEmpty(room.getId())){
                //添加
                room.setId(new ObjectId().toString());
                room.setCreateId(userId);
                room.setCreateName(userName);
                room.setCreateTime(new Date());
                busRoomDao.insert(room);
            }
            else{
                //修改
                Document document = new Document("_id",new ObjectId(room.getId()));
                Document oldRoom =mongoTemplate.getCollection(TableEnum.COMMUNITY_ROOM_TABLE.getName()).find(document).first();

                if (oldRoom==null) return;

                oldRoom.append("updateTime",new Date());
                oldRoom.append("updateId",userId);
                oldRoom.append("updateName",userName);

                if (lay.equals("Lay_roomNo")){
                    oldRoom.append("nominalFloor",room.getNominalFloor());
                    oldRoom.append("roomNo",room.getRoomNo());
                    oldRoom.append("physicsFloor",room.getPhysicsFloor());
                }
                else if(lay.equals("Lay_buildArea")){
                    oldRoom.append("buildArea",room.getBuildArea());
                }
                else if(lay.equals("Lay_innerArea")){
                    oldRoom.append("innerArea",room.getInnerArea());
                }
                else if(lay.equals("Lay_orientation")){
                    oldRoom.append("orientation",room.getOrientation());
                }
                else if(lay.equals("Lay_scenery")){
                    oldRoom.append("scenery",room.getScenery());
                }
                else if(lay.equals("Lay_structure")){
                    oldRoom.append("structure",room.getStructure());
                }
                else if(lay.equals("Lay_initPrice")){
                    oldRoom.append("initPrice",room.getInitPrice());
                }
                else if(lay.equals("Lay_priceCoe")){
                    oldRoom.append("priceCoe",room.getPriceCoe());
                }

                mongoTemplate.getCollection(TableEnum.COMMUNITY_ROOM_TABLE.getName()).updateOne(document,new Document("$set",oldRoom));
            }



    }

    @Override
    public BusRoom getByBuildId(String buildId) {
        return  null;
    }

    @Override
    public BusRoom get(String id) {
        return busRoomDao.selectById(id,BusRoom.class);
    }

    @Override
    public void deleteByBid(String buildingId) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        criteria.and("buildingId").is(buildingId);
        query.addCriteria(criteria);
        mongoTemplate.remove(query,Map.class,TableEnum.COMMUNITY_ROOM_TABLE.getName());
    }

    @Override
    public void updateByBuilding(BusBuilding busBuilding) {

    }
}



