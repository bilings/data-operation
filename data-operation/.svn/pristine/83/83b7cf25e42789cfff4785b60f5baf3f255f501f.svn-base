package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.BusBuilding;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.BusRoom;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.service.estate.BusBuildingService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.service.estate.BusRoomService;
import com.hifo.dataoperation.util.JSONUtil;
import com.hifo.dataoperation.util.StringUtil;
import com.mongodb.client.FindIterable;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 房号管理
 *
 * @author jinzhichen
 * @date 2019/4/23 15:12
 */
@Api(tags = "房号管理")
@RestController
@RequestMapping("/room")
public class BusRoomController extends BasicController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BusRoomService busRoomService;
    @Autowired
    private BusEstateService busEstateService;

    @Autowired
    private BusBuildingService busBuildingService;
    /**
     * 根据楼栋ID查询单元信息
     * @param buildingId
     * @return
     */
    @ApiOperation(value = "根据楼栋id查询楼栋单元信息")
    @GetMapping(value = "/unitList")
    @ApiImplicitParams({@ApiImplicitParam(name = "buildingId", value = "楼栋id", paramType = "query", dataType = "String", required = true)
    })
    @RequiredPermission("100")
    public ApiResult qryUtil(String buildingId) {
        if (StringUtils.isBlank(buildingId)) {
            return ApiResult.failed("参数不能为空");
        }
        BusBuilding build= busBuildingService.findById(buildingId);
        if(build==null){
            return ApiResult.failed("没有查询到数据");
        }
        if (StringUtil.isNull(build.getUnitName())){
            return ApiResult.failed("该楼栋尚未设置单元数，请先去设置吧");
        }
        //List list= Arrays.asList(build.getUnitName().split(","));

        return  ApiResult.success(build);
    }

    /**
     * 根据楼栋id查询房号
     * @param buildingId
     * @return
     */
    @ApiOperation(value = "根据楼栋id查询房号")
    @GetMapping(value = "/roomList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildingId", value = "楼栋id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "util", value = "单元号", paramType = "query", dataType = "String", required = true)
    })
    @RequiredPermission("100")
    public ApiResult RoomList(String buildingId,String util) {
        if (StringUtils.isBlank(buildingId) ||StringUtils.isBlank(util)) {
            return ApiResult.failed("参数不能为空");
        }
        return busRoomService.queryList(buildingId,util);
    }
    /**
     * 封装室号
     * @param buildingId
     * @return
     */
    @ApiOperation(value = "根据楼栋id查询房号")
    @GetMapping(value = "/roomhead")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildingId", value = "楼栋id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "util", value = "单元号", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query", dataType = "String", required = true)
    })
    @RequiredPermission("100")
    public ApiResult room(String buildingId,String util,String type) {
            return ApiResult.success(6);
    }

    /**
     * 生成房号
     * @param
     * @return
     */
    @ApiOperation(value = "根据楼栋id查询房号")
    @PostMapping(value = "/creatRoom",consumes = "application/json; charset=utf-8")
    @ResponseBody
    public ApiResult creatRoomList(@RequestBody Map<String,Object> data)
    {
        String lay=data.get("lay").toString();
        List<Map> list=(List<Map>)data.get("list");
        try {
            BusRoom room=new BusRoom();

            if (list!=null && list.size()>0){
                room= JSONUtil.map2Java(list.get(0),BusRoom.class);
                busRoomService.deleteByBid(room.getBuildingId());
            }

            //批量添加
            for (Map r:list ) {
                BusRoom rm= JSONUtil.map2Java(r,BusRoom.class);
                rm.setId(null);
                busRoomService.save(rm,lay);
            }
            //修改楼栋上的房号数量
            if (list!=null && list.size()>0){
                busEstateService.updateRoomNo(room.getCommunityId());
                busBuildingService.updataRoomNo(room.getBuildingId());
            }
        }
        catch (Exception ex){
            return ApiResult.failed(ex.getMessage());
        }
        return ApiResult.success();
    }


    /**
     * 生成房号
     * @param
     * @return
     */
    @ApiOperation(value = "根据楼栋id查询房号")
    @GetMapping(value = "/data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildingId", value = "楼栋id", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "util", value = "单元号", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "type", value = "类型", paramType = "query", dataType = "String", required = true)
    })
    public ApiResult data() {
        FindIterable<Document> ss = mongoTemplate.getCollection("warehouse_room_service").find(new Document("unit","二单元"));
        for (Document s : ss) {
            s.put("buildArea",s.getInteger("room"));
            mongoTemplate.getCollection("warehouse_room_service").updateOne(new Document("_id",new ObjectId(s.getObjectId("_id").toString())),new Document("$set",s));

        }
        System.out.println("**********************");
        return null;
    }
}
