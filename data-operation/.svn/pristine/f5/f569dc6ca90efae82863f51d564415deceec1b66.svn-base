package com.hifo.dataoperation.service.estate.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.config.CommonThreadPool;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusBuildingDao;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.entity.*;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.mapper.estate.BusEstateMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusEstateAgentService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.service.estate.BusMergeRelService;
import com.hifo.dataoperation.service.estate.BusVirtualRelService;
import com.hifo.dataoperation.service.setting.AdministrativeService;
import com.hifo.dataoperation.service.upload.UploadService;
import com.hifo.dataoperation.util.*;
import com.hifo.dataoperation.vo.BusEstateFailVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import lombok.extern.java.Log;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 楼盘Service接口实现类
 *
 * @author xmw
 * @date 2019/4/25 16:21
 */
@Log
@Service
public class BusEstateServiceImpl extends MybatisPlusService<BusEstateMapper, BusEstate> implements BusEstateService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private BusVirtualRelService virtualRelService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private AdministrativeService administrativeService;
    @Autowired
    private BusEstateDao busEstateDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 导入成功的楼盘
     */
    private Vector<BusEstate> successList = null;

    /**
     * 导入错误的楼盘
     */
    private Vector<BusEstateFailVO> errorList = null;

    //    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult update(BusEstate busEstate) {
        // 添加名字到别名中
        Set<String> otherName = new HashSet<>(busEstate.getOtherName());
        otherName.add(busEstate.getName());
        busEstate.setOtherName(new ArrayList<>(otherName));
        // 添加地址到其他地址
        Set<String> otherAddress =  new HashSet<>(busEstate.getOtherAddress());
        otherAddress.add(busEstate.getAddress());
        busEstate.setOtherAddress(new ArrayList<>(otherAddress));
        //新增
        if (StringUtil.isNull(busEstate.getId())) {
            //检查楼盘是否存在
            Map<String, Object> params = new HashMap<>();
            params.put("cityName", busEstate.getCityName());
            params.put("districtName", busEstate.getDistrictName());
            params.put("name", busEstate.getName());
            params.put("visibility", EntityConstants.PUBLIC);
            List<BusEstate> list = busEstateDao.selectByCondition(params, null, null);
            if (CollectionUtils.isNotEmpty(list)) {
                return ApiResult.failed("新建失败：重复楼盘");
            }
            // 添加楼盘信息
            busEstate.setUpdateTime(new Date());
            busEstate.setCreateDate(new Date());
            busEstateDao.insert(busEstate);
        } else {
            BusEstate busEstate1 = busEstateDao.selectById(busEstate.getId(), BusEstate.class);
            if (busEstate1 == null) {
                return ApiResult.failed("更新失败：楼盘不存在");
            }
            // 更新楼盘信息
            busEstate.setUpdateTime(new Date());
            busEstateDao.updateById(busEstate1.getId(), busEstate);
            // 如果楼盘名字，板块，地址+号 修改了要保存到历史修改表中
            updateEstateLog(busEstate);

        }
        return ApiResult.success(busEstate);
    }

    /**
     * 如果楼盘名字，板块，地址+号 修改了要保存到历史修改表中
     * @param busEstate 楼盘
     */
    private void updateEstateLog(BusEstate busEstate) {
        ExtEmployee employee = (ExtEmployee) RequestUtil.session(RequestUtil.LOGIN_EMPLOYEE);
        Document docCommunity = mongoTemplate.getCollection(TableEnum.COMMUNITY_LOG_TABLE.getName()).find(new Document("communityId",busEstate.getId())).first();
        if(docCommunity!=null){
            Map currentName = (Map)docCommunity.get("currentName");
            Map currentBlock = (Map)docCommunity.get("currentBlock");
            Map currentRoad = (Map)docCommunity.get("currentRoad");
            Map currentNumber = (Map)docCommunity.get("currentNumber");
            // 名称字段
            if(!currentName.get("name").equals(busEstate.getName())){
                Map map = new HashMap();
                docCommunity.put("lastName",docCommunity.get("currentName"));
                map.put("name",busEstate.getName());
                map.put("sr","人工");
                map.put("time", DateTimeUtils.localDateTimeToStr(LocalDateTime.now()));
                map.put("init", employee.getName()+"维护");
                docCommunity.put("currentName",map);
            }
            // 板块字段
            if(currentBlock!=null && StringUtils.isNotBlank(currentBlock.get("name")+"") && !currentBlock.get("name").equals(busEstate.getShangQuan())){
                Map map = new HashMap();
                docCommunity.put("lastBlock",docCommunity.get("currentBlock"));
                map.put("name",busEstate.getShangQuan()==null?"":busEstate.getShangQuan());
                map.put("sr","人工");
                map.put("time", DateTimeUtils.localDateTimeToStr(LocalDateTime.now()));
                map.put("init", employee.getName()+"维护");
                docCommunity.put("currentBlock",map);
            }
            // 道路字段
            if(currentRoad!=null && StringUtils.isNotBlank(currentRoad.get("name")+"") && !currentRoad.get("name").equals(busEstate.getStdAddress().getRoad())){
                Map map = new HashMap();
                docCommunity.put("lastRoad",docCommunity.get("currentRoad"));
                map.put("name",busEstate.getStdAddress().getRoad()==null?"":busEstate.getStdAddress().getRoad());
                map.put("sr","人工");
                map.put("time", DateTimeUtils.localDateTimeToStr(LocalDateTime.now()));
                map.put("init", employee.getName()+"维护");
                docCommunity.put("currentRoad",map);
            }
             // 号字段
            if(currentNumber!=null && StringUtils.isNotBlank(currentNumber.get("name")+"") && !currentNumber.get("name").equals(busEstate.getStdAddress().getNumber())){
                Map map = new HashMap();
                docCommunity.put("lastNumber",docCommunity.get("currentNumber"));
                map.put("name",busEstate.getStdAddress().getNumber()==null?"":busEstate.getStdAddress().getNumber());
                map.put("sr","人工");
                map.put("time", DateTimeUtils.localDateTimeToStr(LocalDateTime.now()));
                map.put("init", employee.getName()+"维护");
                docCommunity.put("currentNumber",map);
            }
             mongoTemplate.getCollection(TableEnum.COMMUNITY_LOG_TABLE.getName()).updateOne(new Document("communityId",busEstate.getId()),new Document("$set",docCommunity));
        }
    }


    @Override
    public ApiResult delete(String[] ids) {
        var length = ids.length;
        return ApiResult.success("删除成功，共删除" + length + "条数据。");
    }

    @Override
    public ApiResult visibility(String[] ids, String visibility) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            Map<String, Object> maps = new HashMap<>();
            maps.put("visibility", visibility);
            busEstateDao.updateByParams(ids[i], maps);
        }
        return ApiResult.success("设置成功，共设置" + length + "条数据。");
    }

    /**
     * 修改经纬度
     * @param busEstate
     * @return
     */
    @Override
    public ApiResult updatePosition(BusEstate busEstate) {
        Document query = new Document("_id",new ObjectId(busEstate.getId()));
        Document update = new Document();
        update.append("location",busEstate.getLocation());
        mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(query,new Document("$set",update));
        return ApiResult.success("修改成功");
    }

    /**
     * 初始化测试数据
     */
    @Override
    public String init() {
        FindIterable<Document> documents = mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).find();
        for (Document document : documents) {
            Document doc = new Document();
            doc.put("communityId",document.getObjectId("_id").toString());
            Map mapName =new HashMap();
            Map mapBlock =new HashMap();
            Map mapRoad =new HashMap();
            Map mapNumber =new HashMap();
            mapName.put("name",document.getString("name"));
            mapName.put("sr",document.getString("sr"));
            mapName.put("time",document.getString("updateDate"));
            mapName.put("init","打底合并");
            doc.put("currentName",mapName);
            doc.put("lastName",mapName);
            if(StringUtils.isBlank(document.getString("shangQuan"))){
                mapBlock.put("name",document.getString("shangQuan"));
                mapBlock.put("sr",document.getString("sr"));
                mapBlock.put("time",document.getString("updateDate"));
                mapBlock.put("init","打底合并");
            }else{
                mapBlock.put("name","");
                mapBlock.put("sr","");
                mapBlock.put("time","");
                mapBlock.put("init","");
            }

            doc.put("currentBlock",mapBlock);
            doc.put("lastBlock",mapBlock);
            Map stdAddress ;
            try{
                 stdAddress = (Map)document.get("stdAddress");
            }catch (Exception e){
                 stdAddress =null;
            }
            if(stdAddress==null){
                mapRoad.put("name","");
                mapRoad.put("sr","");
                mapRoad.put("time","");
                mapRoad.put("init","");
                mapNumber.put("name","");
                mapNumber.put("sr","");
                mapNumber.put("time","");
                mapNumber.put("init","");
            }else{
                mapRoad.put("name",stdAddress.get("road"));
                mapRoad.put("sr",document.getString("sr"));
                mapRoad.put("time",document.getString("updateDate"));
                mapRoad.put("init","打底合并");
                mapNumber.put("name",stdAddress.get("number"));
                mapNumber.put("sr",document.getString("sr"));
                mapNumber.put("time",document.getString("updateDate"));
                mapNumber.put("init","打底合并");
            }
            doc.put("currentRoad",mapRoad);
            doc.put("lastRoad",mapRoad);
            doc.put("currentNumber",mapNumber);
            doc.put("lastNumber",mapNumber);
            mongoTemplate.getCollection(TableEnum.COMMUNITY_LOG_TABLE.getName()).updateOne(new Document("communityId",document.getObjectId("_id").toString()),new Document("$set",doc));
        }
        System.out.println("**************");
        return "成功";
    }

    @Override
    public ApiResult<Map> log(String communityId, String type) {
        Document doc = new Document();
        doc.put("communityId",communityId);
        Document docs = mongoTemplate.getCollection(TableEnum.COMMUNITY_LOG_TABLE.getName()).find(doc).first();
        Map map = new HashMap();
        if("nameIcon".equals(type)){
            map.put("currentName",docs.get("currentName"));
            map.put("lastName",docs.get("lastName"));
       }else if("blockIcon".equals(type)){
            map.put("currentBlock",docs.get("currentBlock"));
            map.put("lastBlock",docs.get("lastBlock"));
        }else if("roadIcon".equals(type)){
            map.put("currentRoad",docs.get("currentRoad"));
            map.put("lastRoad",docs.get("lastRoad"));
        }else if("numberIcon".equals(type)){
            map.put("currentNumber",docs.get("currentNumber"));
            map.put("lastNumber",docs.get("lastNumber"));
        }
        System.out.println(map);
        return ApiResult.success(map);
    }

    /**
     * 合并楼盘
     *
     * @param fromIds
     * @param toId
     * @return
     */
    @Override
    public ApiResult merge(String[] fromIds, String toId) {
        // 1. 获取源楼盘, 目标楼盘
        var to = busEstateDao.selectById(toId, BusEstate.class);
        Set<String> otherName = new HashSet<>(to.getOtherName());
        Set<String> otherAddress =  new HashSet<>(to.getOtherAddress());
        if (to == null) {
            return ApiResult.failed("请选择目标楼盘。");
        }
        // 1. 找出目标楼盘下的楼栋数
        FindIterable<Document> buildingsTo = mongoTemplate.getCollection(TableEnum.BUILDING_STANDARD_TABLE.getName()).find(new Document("communityId",toId));
        Set<String> guidSet = new HashSet<>();
        for (int i = 0, len = fromIds.length; i < len; i++) {
            //不能合并自身楼盘
            if (fromIds[i].equals(toId)) {
                continue;
            }
            var fromCommunity = busEstateDao.selectById(fromIds[i], BusEstate.class);
            List<String> guidFromList = fromCommunity.getGuidList();
            if(guidFromList!=null){
                for (String s : guidFromList) {
                    //标准库中的楼盘关联到服务库中合并中
                    Document query = new Document("guid",s);
                    // 从标准化库中取出
                    Document standardCommunity =  mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName()).find(query).first();
                    // 找出标准楼盘下的案例，将该案例commmunityId 改为合并目标楼盘的ID
                    FindIterable<Document> caseInfo =  mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName()).find(new Document("communityId",standardCommunity.getObjectId("_id").toString()));
                    for (Document document : caseInfo) {
                        mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName()).updateOne(new Document("_id",document.getObjectId("_id")),new Document("$set",new Document("communityId",toId)));
                    }
                }
                guidSet.addAll(guidFromList);
            }
            // 隐藏from楼盘
            mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(fromIds[i])),new Document("$set",new Document("isDeleted",true)));
            // 下架楼盘的价格
            downCommunityPrice(fromIds[i],true);
            List<String> guidToList =  to.getGuidList();
           if(guidToList !=null){
               guidSet.addAll(guidToList);
           }
            FindIterable<Document> buildingsFrom = mongoTemplate.getCollection(TableEnum.BUILDING_STANDARD_TABLE.getName()).find(new Document("communityId",fromIds[i]));
            // 合并楼栋
            mergeBuildingWhitRoom(toId,buildingsTo,buildingsFrom);
            // 添加名字到别名中
            otherName.add(fromCommunity.getName());
            // 添加地址到其他地址
            otherAddress.add(fromCommunity.getAddress());
        }
        // 修改to楼盘的guidList
        mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(toId)),new Document("$set",new Document("guidList",guidSet).append("otherName",otherName).append("otherAddress",otherAddress)));
        return ApiResult.success("合并成功。");
    }

    
   
    
    
    
    /**
     * 上下架该楼盘的价格
     * @param estateId 楼盘ID
     * @param flag 山下架
     */
    private void downCommunityPrice(String estateId,boolean flag) {
        mongoTemplate.getCollection(TableEnum.COMMUNITY_PRICE_TABLE.getName()).updateMany(new Document("communityId",estateId),new Document("$set",new Document("isDeleted",flag)));
    }

    /**
     * 楼栋合并
     * @param toId  目标楼盘ID
     * @param buildingsTo  目标楼盘的楼栋
     * @param buildingsFrom 待合并楼盘的楼栋
     */
    private void mergeBuildingWhitRoom(String toId,FindIterable<Document> buildingsTo, FindIterable<Document> buildingsFrom) {

        if(buildingsTo.first()!=null){
            for (Document docTo : buildingsTo) {
                String toName = docTo.getString("name");
                for (Document docFrom : buildingsFrom) {
                    String fromName = docFrom.getString("name");
                     String checkToName =   checkBuildName(fromName);
                     String checkFromName =   checkBuildName(fromName);
                    //　TODO　楼栋名字一样
                    if(fromName.equals(toName) ||(checkFromName.equalsIgnoreCase(checkToName)&& fromName.contains("单元") && toName.contains("单元"))){
                        // 如果楼栋名字相同
                        FindIterable<Document> roomToList = mongoTemplate.getCollection(TableEnum.ROOM_STANDARD_TABLE.getName()).find(new Document("buildingId",docTo.getObjectId("_id").toString()));
                        FindIterable<Document> roomFromList = mongoTemplate.getCollection(TableEnum.ROOM_STANDARD_TABLE.getName()).find(new Document("buildingId",docFrom.getObjectId("_id").toString()));
                         // 目标楼盘的楼栋没有房号
                        if(roomToList.first()!=null){
                            //带合并的楼栋没有房号
                            if(roomFromList.first()!=null){
                            /*针对1栋（A盘）、1幢（B盘）都有房号的情况，判断每栋楼的楼栋数据的地上总楼层、住宅类别和房号数据的地上总楼层以及地上总楼层对应的住宅类别是否一致，如不一致，用房号数据的楼层反过来修正楼栋数据；
                            判断1栋（A盘）、1幢（B盘）的地上总楼层是否一致：
                            如一致，1幢（可见），1栋（隐藏），用1栋的楼栋基础数据填充1幢 的空值字段数据；*/
                                String to =   docTo.getString("floorTotalNo");
                                String from =   docFrom.getString("floorTotalNo");
                               if(to.equals(from)){
                                   // 楼层一致， 隐藏待合并楼栋 、基础数据填充  TODO （那些属性填充啊？？？？？？？？？）
                                   mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(docFrom.getObjectId("_id").toString())),new Document("$set",new Document("isDeleted",false)));
                               }else {
                                   // 楼层不一致，隐藏待合并楼栋
                                   mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(docFrom.getObjectId("_id").toString())),new Document("$set",new Document("isDeleted",false)));
                               }
                            }else{
                                // 隐藏目标楼盘
                                mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(docTo.getObjectId("_id").toString())),new Document("$set",new Document("isDeleted",false)));
                            }
                        }else{
                            // 隐藏待合并楼栋
                            mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(docFrom.getObjectId("_id").toString())),new Document("$set",new Document("isDeleted",false)));
                        }
                    }
                }
            }
        }else{
            // 如果目标楼盘的楼栋为空,把待合并楼盘下的楼栋和房号复制到该楼栋下
            for (Document document : buildingsFrom) {
                FindIterable<Document> roomList = mongoTemplate.getCollection(TableEnum.ROOM_STANDARD_TABLE.getName()).find(new Document("buildingId",document.getObjectId("_id").toString()));
                for (Document room : roomList) {
                    room.remove("_id");
                    room.put("communityId",toId);
                    room.put("buildingId",document.getObjectId("_id").toString());
                    mongoTemplate.getCollection(TableEnum.ROOM_STANDARD_TABLE.getName()).insertOne(room);
                }
                document.remove("_id");
                document.put("communityId",toId);
                mongoTemplate.getCollection(TableEnum.BUILDING_STANDARD_TABLE.getName()).insertOne(document);
            }
        }
    }

    /**
     * 正则匹配名字
     * @param str
     * @return
     */
    public String checkBuildName(String str){
        try{
            Pattern DONG = Pattern.compile("第?[0-9a-zA-Z]+([栋幢坐号]{1}|[单元]{2})");
            Matcher m=DONG.matcher(str);
            if(m.find()){
             return  m.group();
            }else{
                return "";
            }
        }catch(Exception e){
            return "";
        }
    }

    /**
     * 解除合并
     * @param fromId
     * @param toId
     * @return
     */
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult undoMerge(String fromId, String toId) {
        // 目标楼盘
        BusEstate busEstateTo = busEstateDao.selectById(toId,BusEstate.class);
        // 待解除楼盘
        Document document = mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName()).find(new Document("_id",new ObjectId(fromId))).first();
        List list = busEstateTo.getGuidList();
        Iterator<String> l = list.iterator();
       while (l.hasNext()){
           String guid = l.next();
            if(guid.equals(document.getString("guid"))){
                // 判断服务表中是否有该楼盘。如果有显示出来
                Map map = new HashMap();
                map.put("guid",document.getString("guid"));
                Document doc = mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).find(new Document("guid",document.getString("guid"))).first();
                //  解除案例
                FindIterable<Document> docCases = mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName()).find(new Document("guid",document.getString("guid")));
                for (Document docCase : docCases) {
                    // 案例的communityUrl改成原来的guid
                    mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName()).updateOne(new Document("_id",new ObjectId(docCase.getObjectId("_id").toString())),new Document("$set",new Document("communityUrl",document.getString("guid"))));
                }
                if(doc!=null){
                    mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("guid",document.getString("guid")),new Document("$set",new Document("isDeleted",false)));
                    // 解除楼盘的价格显示出来
                    downCommunityPrice(fromId,false);
                }
                // 解除绑定
                l.remove();
            }
        }
        System.out.println(list);
        mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(new Document("_id",new ObjectId(toId)),new Document("$set",new Document("guidList",list)));
        return ApiResult.success("取消合并成功。");
    }

    /**
     * 虚拟楼盘
     * @param fromIds
     * @param toId
     * @return
     */
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult virtual(String[] fromIds, String toId) {
        var length = fromIds.length;
        // 1. 获取源楼盘, 目标楼盘
        var to = busEstateDao.selectById(toId, BusEstate.class);
        // 2. to的状态改为：虚拟
        to.setIsVirtual(EntityConstants.VIRTUAL);
        busEstateDao.updateById(toId, to);
        for (var i = 0; i < length; i++) {
            // 3. 虚拟记录
            if(fromIds[i].equals(toId)){
                // 自己不能虚拟自己
                continue;
            }
            var from = busEstateDao.selectById(fromIds[i], BusEstate.class);
            from.setVirtualstr(toId);
            // 设置虚拟关系
            busEstateDao.updateById(fromIds[i], from);
        }
        return ApiResult.success("虚拟化成功，共设置" + length + "条数据。");
    }

    /**
     * 取消虚拟
     * @param id
     * @param type
     * @return
     */
    @Override
    public ApiResult undoVirtual(String type,String id) {
       // 解除虚拟到该楼盘：如果B楼盘虚拟到A楼盘下。id为B楼盘的id
        var to = busEstateDao.selectById(id, BusEstate.class);
        to.setVirtualstr("");
        busEstateDao.updateById(id, to);
        return ApiResult.success("取消虚拟化成功。");
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult evaluate(String[] ids, String canEvaluate) {
        var length = ids.length;
        return ApiResult.success("设置成功，共设置" + length + "条数据。");
    }

    @Override
    public Pagination<BusEstate> findByPage(BusEstate busEstate, Pagination page) {
        if(StringUtils.isBlank(busEstate.getCityId())){
            return new Pagination<>();
        }
        var map = administrativeService.getMapByParentId(busEstate.getCityId());
        var employee = basicService.getLoginEmployee();
        Query query = findListQuery(busEstate, employee, page);
        Pagination<BusEstate> busEstatesList = busEstateDao.getPageLists(page.getPage(), page.getLimit(), query);
        busEstatesList.getRecords().stream().forEach(e -> {
            if (StringUtil.isEmpty(e.getCityId())) {
                e.setCityName(map.get(e.getCityId()));
            }
            if (StringUtil.isEmpty(e.getDistrictId())) {
                e.setDistrictName(map.get(e.getDistrictId()));
            }
        });
        return busEstatesList;
    }

    /**
     * 格式化列表查询条件
     *
     * @param busEstate
     * @param employee
     * @param page
     * @return
     */
    public static Query findListQuery(BusEstate busEstate, ExtEmployee employee, Pagination page) {
//        // 设置机构
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtil.isNotEmpty(busEstate.getCityId())) {
            criteria.and("cityId").is(busEstate.getCityId());
        }
        if (StringUtil.isNotEmpty(busEstate.getDistrictId())) {
            criteria.and("districtId").is(busEstate.getDistrictId());
        }
        if (StringUtil.isNotEmpty(busEstate.getDistrictName())) {
            criteria.and("districtName").is(busEstate.getDistrictName());
        }
        if (StringUtil.isNotEmpty(busEstate.getName())) {
            Pattern pattern = Pattern.compile("^.*" + busEstate.getName() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(Criteria.where("name").regex(pattern),
                    Criteria.where("otherName").regex(pattern),
                    Criteria.where("otherAddress").regex(pattern)
            );
        }
        if (StringUtil.isNotEmpty(busEstate.getIsSpecial())) {
            criteria.and("isSpecial").is(busEstate.getIsSpecial());
        }
        if (StringUtil.isNotEmpty(busEstate.getVisibility())) {
            criteria.and("visibility").is(busEstate.getVisibility());
        }
        if (StringUtil.isNotEmpty(busEstate.getIsVirtual())) {
            criteria.and("isVirtual").is(busEstate.getIsVirtual());
        }
        // 不是删除楼盘
        criteria.and("isDeleted").is(false);
        // 是否是代建楼盘
        query.addCriteria(criteria);
        if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "updateTime")));
        }
        return query;
    }

    /**
     * 获取楼盘合并记录
     *
     * @param id
     * @return
     */
    @Override
    public ApiResult<List<Document>> getMergeListByToId(String id) {
        BusEstate busEstate = busEstateDao.selectById(id,BusEstate.class);
        List<String> guidList = busEstate.getGuidList();
        Document queryCondition = new Document();
        queryCondition.put("guid", new BasicDBObject("$in", guidList));
        FindIterable<Document> list = mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName()).find(queryCondition);
        List<Document> listVo = new ArrayList<>();
        for (Document document : list) {
            document.put("id",document.getObjectId("_id").toString());
            listVo.add(document);
        }
        return ApiResult.success(listVo);
    }

    /**
     * 虚拟记录到其他楼盘
     * @param id
     * @return
     */
    @Override
    public ApiResult<List<BusEstate>> getVirtualListByFromId(String id) {
        BusEstate busEstate = busEstateDao.selectById(id, BusEstate.class);
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").is(busEstate.getVirtualstr());
        query.addCriteria(criteria);
        List<BusEstate> list = mongoTemplate.find(query, BusEstate.class);
        return ApiResult.success(list);
    }

    /**
     * 楼盘虚拟记录虚拟至该楼盘的
     * @param id
     * @return
     */
    @Override
    public ApiResult<List<BusEstate>> getVirtualListByToId(String id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("virtualstr").is(id);
        query.addCriteria(criteria);
        List<BusEstate> list = mongoTemplate.find(query, BusEstate.class);
        return ApiResult.success(list);
    }

    /**
     * 更新已建楼栋
     *
     * @param id 楼盘id
     */
    @Override
    public void updateBuildingNo(String id) {
        Document document = new Document("_id",new ObjectId(id));
        Document d =mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).find(document).first();
        if (d!=null){
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("communityId").is(id);
            criteria.and("isDeleted").ne(1);
            criteria.and("visibility").ne("0");
            query.addCriteria(criteria);
            Long count=mongoTemplate.count(query,BusBuilding.class);
            d.append("totalNumberOfBuildings",count.toString());
            mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(document,new Document("$set",d));

        }
    }

    /**
     * 更新已建房号数
     *
     * @param id 楼盘id
     */
    @Override
    public void updateRoomNo(String id) {
        Document document = new Document("_id",new ObjectId(id));
        Document d =mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).find(document).first();
        if (d!=null){

            Long count=mongoTemplate.getCollection(TableEnum.COMMUNITY_ROOM_TABLE.getName()).countDocuments(new Document("communityId",id));
            d.append("totalRoomCount",count.toString());
            mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(document,new Document("$set",d));

        }

    }

    @Override
    public List<BusEstate> queryByParam(BusEstate busEstate) {
        QueryWrapper<BusEstate> queryWrapper = new QueryWrapper<BusEstate>();
        queryWrapper.eq("cityName", busEstate.getCityName());
        queryWrapper.eq("districtName", busEstate.getDistrictName());
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 判断是否为正确楼盘（用于导入判断）
     *
     * @param busEstate 楼盘
     * @return 返回空表示没有错误信息没
     */
    public String getErrMsgByImport(BusEstate busEstate, ExtEmployee emp) {
        // 判断数据有效性
        if (busEstate == null) {
            return "空楼盘";
        }
        if (StringUtil.isNull(busEstate.getCityName())) {
            return "缺少必要信息：城市";
        }
        if (StringUtil.isNull(busEstate.getDistrictName())) {
            return "缺少必要信息：行政区";
        }
        return null;
    }

    @Async
    public void executeAsyncByBusEstate(Map<String, Integer> dictMap, BusEstate m, ExtEmployee emp) {

    }

    @Override
    public String importEstate(MultipartFile file) throws Exception {
        // excel转list
        List<BusEstate> list = ExcelUtil.importExcel(file, 1, 1, BusEstate.class);
        var size = list.size();
        if (size == 0) {
            return "空文件";
        }

        // 字典map
        var dictMap = new HashMap<String, Integer>(50);
//        var maps = dictionaryService.listMaps(new QueryWrapper<>());
//        for (var i = 0; i < maps.size(); i++) {
//            dictMap.put((String) maps.get(i).get("item"), (Integer) maps.get(i).get("id"));
//        }
        var emp = basicService.getLoginEmployee();
        // 重新设置
        successList = new Vector<>();
        errorList = new Vector<>();
        // 楼盘数据入库
        List<Future> futureList = new ArrayList<>();
        if (emp != null) {
            for (var i = 0; i < size; i++) {
                int finalI = i;
                Future future = CommonThreadPool.submit(() -> {
                    executeAsyncByBusEstate(dictMap, list.get(finalI), emp);
                });
                futureList.add(future);
            }
            for (Future f : futureList) {
                f.get();
            }
        }
        // 批量插入楼盘
        if (successList.size() > 0) {
            insertBatch(successList, Sequence.seq_bus_estate, Table.bus_estate);
        }
        // 下载导入的错误数据excel
        var errorSize = errorList.size();
        File failedFile = uploadService.downloadFailedFile(new ExportParams("导入错误楼盘信息", "失败数据"), BusEstateFailVO.class, errorList);
        // 记录上传日志
        uploadService.save(file.getOriginalFilename(), size - errorSize, errorSize, failedFile.getAbsolutePath(), "楼盘");
        return failedFile.getAbsolutePath();
    }

    /**
     * 数据字典数据转换
     *
     * @param map
     * @param ids
     * @return
     */
    public static String formatDictByIds(Map<Integer, String> map, String ids) {
        var s = StringUtils.split(ids, ",");
        for (var i = 0; i < s.length; i++) {
            if (StringUtil.isNull(s[i])) {
                s[i] = "";
            } else {
                s[i] = map.get(Integer.parseInt(s[i]));
            }
        }
        return StringUtils.join(s, ",");
    }

    /**
     * 数据字典数据转换
     *
     * @param map
     * @param items
     * @return
     */
    public static String formatDictByItems(Map<String, Integer> map, String items) {
        var s = StringUtils.split(items, ",");
        for (var i = 0; i < s.length; i++) {
            if (Double.isNaN(map.get(s[i]))) {
                s[i] = "";
            } else {
                s[i] = map.get(s[i]) + "";
            }
        }
        return StringUtils.join(s, ",");
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult agent(String[] ids) {
        var length = ids.length;
//        for (var i = 0; i < length; i++) {
//            var obj = baseMapper.selectById(ids[i]);
//            if (obj != null) {
//                obj.setIsAgent(false);
//                updateById(obj);
//                // 生成均价
//                avgPriceService.updateQuality(obj);
//            }
//        }
        return ApiResult.success("新建成功，共新建" + length + "个楼盘。");
    }

    @Override
    public BusEstate getDetailById(String id) {
        BusEstate busEstate = busEstateDao.selectById(id, BusEstate.class);

        return busEstate;
    }

}

