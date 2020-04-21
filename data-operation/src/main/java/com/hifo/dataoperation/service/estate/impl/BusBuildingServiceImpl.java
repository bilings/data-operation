package com.hifo.dataoperation.service.estate.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusBuildingDao;
import com.hifo.dataoperation.entity.BusBuilding;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.BusRoom;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.mapper.estate.BusBuildingMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.estate.BusBuildingService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.service.estate.BusRoomService;
import com.hifo.dataoperation.service.setting.DictionaryService;
import com.hifo.dataoperation.service.upload.UploadService;
import com.hifo.dataoperation.util.*;
import com.hifo.dataoperation.vo.BusBuildingFailVO;
import com.hifo.dataoperation.vo.BusBuildingVO;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import lombok.extern.java.Log;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

/**
 * @author bxl
 * @create 18:21
 */
@Log
@Service
public class BusBuildingServiceImpl extends MybatisPlusService<BusBuildingMapper, BusBuilding> implements BusBuildingService {

    public static final String collectName="building";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BusEstateService estateService;
    @Autowired
    private BasicService basicService;
    @Autowired
    private BusRoomService busRoomService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private UploadService uploadService;

    @Autowired
    private BusBuildingDao busBuildingDao;

    @Override
    public Pagination<BusBuilding> list(Pagination pages, BusBuilding busBuilding) {
        Query query=findByPage(pages,busBuilding);
        Pagination<BusBuilding> busBuildingList = busBuildingDao.getPageLists(pages.getPage(),pages.getLimit(),query);
        return busBuildingList;
    }



    @Override
    public void hideBuilding(String[] ids) {
        for (String id : ids) {
            Document document = new Document("_id",new ObjectId(id));
            Document d =mongoTemplate.getCollection("building").find(document).first();
            d.append("visibility","0");

            mongoTemplate.getCollection("building").updateOne(document,new Document("$set",d));
            //busBuildingDao.updateById(id,busBuilding);
        }
    }

    @Override
    public void showBuilding(String[] ids) {
        for (String id : ids) {
            Document document = new Document("_id",new ObjectId(id));
            Document d =mongoTemplate.getCollection("building").find(document).first();
            d.append("visibility","1");

            mongoTemplate.getCollection("building").updateOne(document,new Document("$set",d));
        }
    }

    @Override
    public String transferBuilding(long estateId, String transferIds) {
        String[] str = transferIds.split(",");
        List list = Arrays.asList(str);
        baseMapper.transferBuilding(estateId, list);
        //实时统计楼盘下的楼栋数(这个是转移到的楼盘)
        estateService.updateBuildingNo(estateId + "");
        //实时统计楼盘下的楼栋数(这个是被转移到的楼盘)
        BusBuilding busBuilding = baseMapper.selectById(parseLong(list.get(0).toString()));
        estateService.updateBuildingNo(busBuilding.getCommunityId());
        return null;
    }

    @Override
    public BusBuilding findById(String id) {
        return busBuildingDao.selectById(id,BusBuilding.class);
    }

    @Override
    public void fillSave(String[] colName, String fillIds, String id) {
        if (StringUtils.isNotEmpty(fillIds)) {
            List<String> ids = Arrays.asList(fillIds.split(","));
            BusBuilding fillBuliding = baseMapper.selectById(id);
            for (String sid : ids) {
                BusBuilding busBuilding = baseMapper.findById(Long.parseLong(sid));
                for (int i = 0; i < colName.length; i++) {
                    busBuilding = fillColData(busBuilding, fillBuliding, colName[i]);
                }
                baseMapper.fillSaveOne(busBuilding);
            }
        }
    }

    /**
     * 填充要填充的字段值
     *
     * @param s
     * @return
     */
    private BusBuilding fillColData(BusBuilding busBuilding, BusBuilding fillBuliding, String s) {
        switch (s) {
            case "otherName":
                busBuilding.setOtherName(fillBuliding.getOtherName());
                break;
            case "cell":
                busBuilding.setUnitNo(fillBuliding.getUnitNo());
                break;
            case "floorOverGroundNo":
                busBuilding.setOverGroundFloor(fillBuliding.getOverGroundFloor());
                break;
            case "floorUnderGroundNo":
                busBuilding.setUnderGroundFloor(fillBuliding.getUnderGroundFloor());
                break;
            case "floorTotalNo":
                busBuilding.setTotalFloor(fillBuliding.getTotalFloor());
                break;
            case "builtDate":
                busBuilding.setBuiltDate(fillBuliding.getBuiltDate());
                break;
            case "liftNo":
                busBuilding.setLiftNo(fillBuliding.getLiftNo());
                break;
            case "liftBrand":
                busBuilding.setLiftBrand(fillBuliding.getLiftBrand());
                break;
            case "address":
                busBuilding.setAddress(fillBuliding.getAddress());
                break;
            case "structure":
                busBuilding.setStructure(fillBuliding.getStructure());
                break;
            case "buildingType":
                busBuilding.setBuildingType(fillBuliding.getBuildingType());
                break;
            case "priceCoe":
                busBuilding.setPriceCoe(fillBuliding.getPriceCoe());
                break;
            case "wallDecoration":
                busBuilding.setWallDecoration(fillBuliding.getWallDecoration());
                break;
            case "nonStandDesc":
                busBuilding.setNonStandDesc(fillBuliding.getNonStandDesc());
                break;
            case "priceDesc":
                busBuilding.setPriceDesc(fillBuliding.getPriceDesc());
                break;
            default:
                break;
        }
        return busBuilding;
    }

    @Override
    public void delete(String[] ids) {
        BusBuilding busBuilding = null;
        for (String id : ids) {
            Document document = new Document("_id",new ObjectId(id));
            Document d =mongoTemplate.getCollection("building").find(document).first();
            d.append("isDeleted","1");
            mongoTemplate.getCollection("building").updateOne(document,new Document("$set",d));
        }
        if (busBuilding != null) {
            //实时统计楼盘下的楼栋数
            //estateService.updateBuildingNo(busBuilding.getCommunityId() + "");
        }
    }

    @Override
    public void undelete(String[] ids) {
        //取消删除时，把楼栋的状态设置为后台可见 1
        BusBuilding busBuilding = null;
        for (String id : ids) {
            Document document=new Document("_id",new ObjectId(id));
            Document d =mongoTemplate.getCollection("building").find(document).first();
            d.append("isDeleted","0");
            mongoTemplate.getCollection("building").updateOne(document,new Document("$set",d));
        }
        if (busBuilding != null) {
            //实时统计楼盘下的楼栋数
            //estateService.updateBuildingNo(busBuilding.getCommunityId() + "");
        }
    }

    @Override
    public String copy(BusBuilding busBuilding) {
        String oldId = busBuilding.getId();
        busBuilding.setId(null);
        busBuilding.setUpdateDate(null);
        if (busBuilding.getId() == null) {
            busBuildingDao.insert(busBuilding);
        } else {
            busBuildingDao.updateById(busBuilding.getId(),busBuilding);
        }
        // 把房号复制过去

        Document document = new Document("buildingId",new ObjectId(oldId));
        document.append("organizationId",basicService.getLoginEmployee().getOrganizationId());
        FindIterable<Document> roomList =  mongoTemplate.getCollection("warehouse_room_service").find(document);

        for(Document room:roomList){
            room.append("_id",new ObjectId());
            room.append("buildingId",busBuilding.getId());

            mongoTemplate.getCollection("warehouse_room_service").updateOne(document,new Document("$set",room));
        }

        //实时统计楼盘下的楼栋数
        estateService.updateBuildingNo(busBuilding.getCommunityId() + "");
        return null;
    }

    @Override
    public Query findByPage(Pagination pages,BusBuilding busBuilding ) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtil.isNotEmpty(busBuilding.getCommunityId())) {
            criteria.and("communityId").is(busBuilding.getCommunityId());
        }
        if (StringUtil.isNotEmpty(busBuilding.getBuildingName())) {
            Pattern pattern= Pattern.compile("^.*"+busBuilding.getBuildingName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(Criteria.where("buildingName").regex(pattern),
                    Criteria.where("otherName").regex(pattern)
            );
        }
        if (StringUtil.isNotEmpty(busBuilding.getBuildingType())) {
            criteria.and("buildingType").is(busBuilding.getBuildingType());
        }
        if (StringUtil.isNotEmpty(busBuilding.getIsDeleted())) {
            criteria.and("isDeleted").is(busBuilding.getIsDeleted());
        }
        if (StringUtil.isNotEmpty(busBuilding.getVisibility())) {
            criteria.and("visibility").is(busBuilding.getVisibility());
        }

        query.addCriteria(criteria);

        /*if (StringUtil.isEmpty(pages.descs()) &&"name".equals(pages.getDescs()[0])){
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "REPLACE(name,'幢','')+0")));
        }
        if (StringUtil.isEmpty(pages.ascs()) && "name".equals(pages.getAscs()[0])) {
            query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "REPLACE(name,'幢','')+0")));
        }*/

        return query;
    }

    @Override
    public String saveBuilding(BusBuilding busBuilding) {
        busBuilding.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        busBuilding.setCreateId(Integer.parseInt(basicService.getLoginEmployee().getId().toString()));
        busBuilding.setCreateName(basicService.getLoginEmployee().getName());
        // ID 为空说明是新增楼栋 保存之前判断楼栋是否重复
        if (StringUtil.isNull(busBuilding.getId())) {
            Query query = new Query();
            Criteria criteria = new Criteria();

            criteria.and("visibility").is("1");
            if (StringUtil.isNotEmpty(busBuilding.getCommunityId())) {
                criteria.and("communityId").is(busBuilding.getCommunityId());
            }
            if (StringUtil.isNotEmpty(busBuilding.getBuildingName())) {
                Pattern pattern= Pattern.compile("^.*"+busBuilding.getBuildingName()+".*$", Pattern.CASE_INSENSITIVE);
                criteria.and("buildingName").regex(pattern);
            }
            query.addCriteria(criteria);
            List<BusBuilding> buildings =busBuildingDao.selectByQuery(query,BusBuilding.class);
            for (BusBuilding building : buildings) {
                if (HkbUtil.isTheSameBuilding(building.getBuildingName(), busBuilding.getBuildingName())) {
                    return "楼栋已存在";
                }
            }
        }
        if(StringUtil.isNotNull(busBuilding.getUnitNo()) ){
            Integer unitNo=Integer.parseInt(busBuilding.getUnitNo());
            var unitName="";
            for(Integer i=1;i<=unitNo;i++)
            {
                unitName+=i+"单元,";
            }
            unitName=unitName.substring(0,unitName.length()-1);
            busBuilding.setUnitName(unitName);
        }
        if (StringUtil.isNotNull(busBuilding.getOtherName())){
            busBuilding.setNameSet(busBuilding.getOtherName().split(","));
        }
        busBuilding.setFetchTime(new Date());

        if(StringUtil.isNotNull(busBuilding.getCommunityId())){
            BusEstate busEstate=estateService.getDetailById(busBuilding.getCommunityId());
            if (busEstate!=null){
                busBuilding.setCommunityName(busEstate.getCommunityName());
            }
        }
        if (StringUtil.isNull(busBuilding.getId())) {
            busBuilding.setId(new ObjectId().toString());
            busBuilding.setDataFrom("人工添加");
            busBuilding.setCreateDate(new Date());
            busBuildingDao.insert(busBuilding);
        } else {
            busBuilding.setUpdateDate(new Date());
            busBuildingDao.updateById(busBuilding.getId(),busBuilding);
        }

        if(StringUtil.isNotEmpty(busBuilding.getCommunityId())){
            //实时统计楼盘下的楼栋数
            estateService.updateBuildingNo(busBuilding.getCommunityId());
        }
        return "1";
    }

    @Override
    public String mergeBuilding(String[] fromIds, String toId) {
        List<BusBuilding> list = baseMapper.selectBatchIds(Arrays.asList(fromIds));
        BusBuilding busBuilding = baseMapper.selectById(toId);
        // 建筑年代
        if (!StringUtils.isNotBlank(busBuilding.getBuiltDate().toString())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getBuiltDate().toString())) {
                    busBuilding.setBuiltDate(building.getBuiltDate());
                }
            }
        }
        // 电梯数
        if (busBuilding.getLiftNo() == "0") {
            for (BusBuilding building : list) {
                if (busBuilding.getLiftNo() != "0") {
                    busBuilding.setLiftNo(building.getLiftNo());
                }
            }
        }
        // 电梯品牌
        if (!StringUtils.isNotBlank(busBuilding.getLiftBrand())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getLiftBrand())) {
                    busBuilding.setLiftBrand(building.getLiftBrand());
                }
            }
        }
        // 建筑结构
        if (!StringUtils.isNotBlank(busBuilding.getStructure())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getStructure())) {
                    busBuilding.setStructure(building.getStructure());
                }
            }
        }
        // 建筑类别
        if (!StringUtils.isNotBlank(busBuilding.getBuildingType())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getBuildingType())) {
                    busBuilding.setBuildingType(building.getBuildingType());
                }
            }
        }
        // 占地面积
        if (!StringUtils.isNotBlank(busBuilding.getBuildingType())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getBuildingType())) {
                    busBuilding.setBuildingType(building.getBuildingType());
                }
            }
        }
       /* // 住宅品质
        if (!StringUtils.isNotBlank(busBuilding.getQuality())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(building.getQuality())) {
                    busBuilding.setQuality(building.getQuality());
                }
            }
        }*/
        // 价格系数
        if (busBuilding.getPriceCoe() == "0") {
            for (BusBuilding building : list) {
                if (building.getPriceCoe() != "0") {
                    busBuilding.setPriceCoe(building.getPriceCoe());
                }
            }
        }
       /* // 最新均价
        if (busBuilding.getAvgPrice() == 0) {
            for (BusBuilding building : list) {
                if (building.getAvgPrice() != 0) {
                    busBuilding.setAvgPrice(building.getAvgPrice());
                }
            }
        }*/
        // 外墙装饰
        if (!StringUtils.isNotBlank(busBuilding.getWallDecoration())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(busBuilding.getWallDecoration())) {
                    busBuilding.setWallDecoration(building.getWallDecoration());
                }
            }
        }
        // 公共装修档次
        if (!StringUtils.isNotBlank(busBuilding.getPublicDecoration())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(busBuilding.getPublicDecoration())) {
                    busBuilding.setPublicDecoration(building.getPublicDecoration());
                }
            }
        }
        // roomNoPerFloor 标准层室号数
        if (busBuilding.getLayerNumber() == "0") {
            for (BusBuilding building : list) {
                if (busBuilding.getLayerNumber() != "0") {
                    busBuilding.setLayerNumber(building.getLayerNumber());
                }
            }
        }
        // floorUnderGroundNo 地下总楼层
        if (busBuilding.getUnderGroundFloor() == "0") {
            for (BusBuilding building : list) {
                if (busBuilding.getUnderGroundFloor() != "0") {
                    busBuilding.setUnderGroundFloor(building.getUnderGroundFloor());
                }
            }
        }
        // 地下总楼层
        if (busBuilding.getOverGroundFloor() == "0") {
            for (BusBuilding building : list) {
                if (busBuilding.getOverGroundFloor() != "0") {
                    busBuilding.setOverGroundFloor(building.getOverGroundFloor());
                }
            }
        }
        // unitNo
        if (busBuilding.getUnitNo() == "0") {
            for (BusBuilding building : list) {
                if (busBuilding.getUnitNo() != "0") {
                    busBuilding.setUnitNo(building.getUnitNo());
                }
            }
        }
        // 别名
        if (!StringUtils.isNotBlank(busBuilding.getOtherName())) {
            for (BusBuilding building : list) {
                if (StringUtils.isNotBlank(busBuilding.getOtherName())) {
                    busBuilding.setOtherName(building.getOtherName());
                }
            }
        }
        // 隐藏数据  修改别合并的楼栋隐藏
        for (BusBuilding building : list) {
            building.setVisibility("0");
            baseMapper.updateById(building);
        }
        // 修改要合并的Id
        baseMapper.updateById(busBuilding);
        return "成功";
    }

    /**
     * 判断是否为正确楼栋（用于新增判断）
     *
     * @param busBuilding 楼栋
     * @return 返回空表示没有错误信息
     */
    private Object getErrMsg(BusBuildingVO busBuilding) {
        long count;
        // 有ID，那么必须存在
        if (!StringUtil.isNull(busBuilding.getId())) {
            // 通过ID，判断楼栋是否存在
            count = basicService.countWithAnd(Table.bus_building, Field.pk(busBuilding.getId()),
                    new Field("organizationId", basicService.getLoginEmployee().getOrganizationId().toString()));
            if (count == 0) {
                return "根据ID未找到匹配楼栋";
            }
        } else {
            // 判断数据有效性
            if (busBuilding == null) {
                return "空楼栋";
            }
            if (StringUtil.isNull(busBuilding.getCityName())) {
                return "缺少必要信息：城市名称";
            }
            if (StringUtil.isNull(busBuilding.getDistrictName())) {
                return "缺少必要信息：行政区名称";
            }
            if (StringUtil.isNull(busBuilding.getEstateName())) {
                return "缺少必要信息：楼盘名称";
            }
            if (StringUtil.isNull(busBuilding.getName())) {
                return "缺少必要信息：楼栋名";
            }
            //根据城市，行政区，楼盘名称。查询出楼盘
            BusEstate busEstate = new BusEstate();
//            busEstate.setName(busBuilding.getEstateName());
            busEstate.setCityName(busBuilding.getCityName());
            busEstate.setDistrictName(busBuilding.getDistrictName());
//            busEstate.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
            List<BusEstate> estates = estateService.queryByParam(busEstate);
            if (estates == null || estates.size() == 0) {
                return "没有找到对应的楼盘";
            } else {
                if (estates.size() > 1) {
                    return "找到对应的楼盘数量大于等于2个";
                } else {
                    //给该楼栋添加楼盘ID
//                    busBuilding.setEstateId(estates.get(0).getId());
                }
            }

            // 在这里可以做个缓存（避免减少数据开销）
            // 通过楼盘ID，楼栋名判断是否已经存在相同楼栋
//            List<BusBuilding> buildings = baseMapper.getBuildingList(estates.get(0).getId(), "", "", "", "");
//            for (BusBuilding building : buildings) {
//                if (HkbUtil.isTheSameBuilding(building.getName(), busBuilding.getName())) {
//                    return "重复楼栋";
//                }
//            }
        }
        return busBuilding;
    }

    /**
     * 保存楼栋（新增、更新）
     *
     * @param busBuilding 楼栋对象
     */
    public String update(BusBuilding busBuilding) {
        // 组织机构
        busBuilding.setOrganizationId(basicService.getLoginEmployee().getOrganizationId());
        // 新增时
        if (StringUtil.isNull(busBuilding.getId())) {
            // 设置创建人信息
            busBuilding.setCreateId(basicService.getLoginEmployee().getId().intValue());
            busBuilding.setCreateName(basicService.getLoginEmployee().getName());
        }
        // 更新
        updateById(busBuilding);
        //实时统计楼盘下的楼栋
        estateService.updateBuildingNo(busBuilding.getCommunityId() );
        return "新建成功";

    }



    /**
     * 根据楼栋id查询楼栋信息
     *
     * @return ApiResult
     */
    @Override
    public ApiResult qryBusBuildings(BusBuilding entity) {
        return ApiResult.success(baseMapper.selectById(entity.getId()));
    }

    /**
     * 根据楼栋id查询楼栋信息
     *
     * @return BusBuilding
     */
    @Override
    public BusBuilding qryBusBuilding(BusBuilding entity) {
        return baseMapper.selectById(entity.getId());
    }

    /**
     * 编辑楼栋信息
     *
     * @return boolean
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult updateBuilding(String id, BusBuilding entity) {
        //entity.get_id(id);
        if (this.updateById(entity)) {
            return ApiResult.success(entity);
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 格式化列表查询条件
     *
     * @param estateId
     * @param employee
     * @return
     */
    public static QueryWrapper findListQuery(Long estateId, ExtEmployee employee) {
        var query = new QueryWrapper<BusEstate>();
        // 设置机构
        query.eq("organizationId", employee.getOrganizationId());
        if (estateId != null) {
            query.eq("estateId", estateId);
        }
        return query;
    }

    @Override
    public List<Map<String, Object>> findMaps(long estateId) {
        // 字典map
        var dictMap = new HashMap<Integer, String>(50);
//        var maps = dictionaryService.listMaps(new QueryWrapper<>());
//        for (var i = 0; i < maps.size(); i++) {
//            dictMap.put((Integer) maps.get(i).get("id"), (String) maps.get(i).get("item"));
//        }
        var employee = basicService.getLoginEmployee();
        Pagination page = new Pagination();
        page.setLimit(50000);
        List<Map<String, Object>> list = baseMapper.selectMapsPage(page, findListQuery(estateId, employee)).getRecords();
        list.stream().forEach(e -> {
            // 建筑结构
            var structure = (String) e.get("structure");
            if (StringUtil.isNotEmpty(structure)) {
                try {
                    e.put("structure", dictMap.get(Integer.parseInt(structure)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            // 建筑类别
            var buildingType = (String) e.get("buildingType");
            if (StringUtil.isNotEmpty(buildingType)) {
                try {
                    e.put("buildingType", dictMap.get(Integer.parseInt(buildingType)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            //住宅品质
            var quality = (String) e.get("quality");
            if (StringUtil.isNotEmpty(quality)) {
                try {
                    e.put("quality", dictMap.get(Integer.parseInt(quality)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            //外墙装饰
            var wallDecoration = (String) e.get("wallDecoration");
            if (StringUtil.isNotEmpty(wallDecoration)) {
                try {
                    e.put("wallDecoration", dictMap.get(Integer.parseInt(wallDecoration)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            //公共装修档次
            var publicDecoration = (String) e.get("publicDecoration");
            if (StringUtil.isNotEmpty(publicDecoration)) {
                try {
                    e.put("publicDecoration", dictMap.get(Integer.parseInt(publicDecoration)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            //电梯
            var haveLift = e.get("haveLift") + "";
            if (StringUtil.isNotEmpty(haveLift)) {
                try {
                    e.put("haveLift", dictMap.get(Integer.parseInt(haveLift)));
                } catch (Exception ex) {
                    log.warning(ex.getMessage());
                }
            }
            e.put("canEvaluate", "1".equals(e.get("canEvaluate")) ? "可估" : "不可估");
            e.put("isPerfect", "1".equals(e.get("isPerfect")) ? "完善" : "不完善");
        });
        return list;
    }

    @Override
    public List<BusBuilding> findLists(long estateId) {
        var employee = basicService.getLoginEmployee();
        Pagination page = new Pagination();
        page.setLimit(50000);
        return baseMapper.selectPage(page, findListQuery(estateId, employee)).getRecords();
    }

    @Override
    public String importBuilding(MultipartFile file) throws Exception {
        // excel转list
        List<BusBuildingVO> list = ExcelUtil.importExcel(file, 1, 1, BusBuildingVO.class);
        var size = list.size();
        if (size == 0) {
            return "空文件";
        }
        List<BusBuildingFailVO> errorList = new ArrayList<>();
        BusBuildingFailVO vo = new BusBuildingFailVO();
        for (var i = 0; i < size; i++) {
            var busBuilding = list.get(i);
            Object errMsg = getErrMsg(busBuilding);
            if (errMsg instanceof String) {
                EntityUtils.copyProperties(busBuilding, vo);
                vo.setReason(errMsg.toString());
                errorList.add(vo);
                continue;
            } else {
                busBuilding = (BusBuildingVO) errMsg;
            }
            try {
                BusBuilding b = new BusBuilding();
                EntityUtils.copyProperties(busBuilding, b);
                if (StringUtil.isNotEmpty(b.getIsPerfect())) {
                    b.setIsPerfect(b.getIsPerfect());
                }
                if (StringUtil.isNotEmpty(b.getIsPerfect())) {
                    b.setIsPerfect(b.getIsPerfect());
                }
                if (b.getId() != null) {
                    updateById(b);
                    continue;
                }
                b.setVisibility("2");
                insert(b, Sequence.seq_bus_building, Table.bus_building);
                estateService.updateBuildingNo(b.getCommunityId() + "");
            } catch (Exception e) {
                vo.setReason(e.getMessage());
                errorList.add(vo);
            }
        }

        // 下载导入的错误数据excel
        var errorSize = errorList.size();
        File failedFile = uploadService.downloadFailedFile(new ExportParams("导入错误楼栋信息", "失败数据"), BusBuildingFailVO.class, errorList);
        // 记录上传日志
        uploadService.save(file.getOriginalFilename(), size - errorSize, errorSize, failedFile.getAbsolutePath(), "楼栋");
        return failedFile.getAbsolutePath();
    }

    @Override
    public void updataRoomNo(String id) {
        Document document = new Document("_id",new ObjectId(id));
        Document d =mongoTemplate.getCollection(TableEnum.BUILDING_TABLE.getName()).find(document).first();
        if (d!=null){
            Long count=mongoTemplate.getCollection(TableEnum.COMMUNITY_ROOM_TABLE.getName()).countDocuments(new Document("buildingId",id));
            d.append("roomNo",count.toString());
            mongoTemplate.getCollection(TableEnum.BUILDING_TABLE.getName()).updateOne(document,new Document("$set",d));
        }
    }

    /**
     * 初始化楼栋 数据源测试数据
     * @return
     */
    @Override
    public String init() {
        FindIterable<Document> documents = mongoTemplate.getCollection(TableEnum.BUILDING_TABLE.getName()).find();
        for (Document document : documents) {
            Document doc = new Document();
            doc.put("buildingId",document.getObjectId("_id").toString());
            Map mapName =new HashMap();

            mapName.put("name",document.getString("buildingName"));
            mapName.put("sr",document.getString("sr"));


            mapName.put("time",document.getDate("updateDate"));
            mapName.put("init","打底合并");
            doc.put("currentName",mapName);
            doc.put("lastName",mapName);

            mongoTemplate.getCollection(TableEnum.BUILDING_LOG_TABLE.getName()).insertOne(doc);
        }
        System.out.println("**************building");
        return "成功";
    }

    @Override
    public ApiResult<Map> log(String buildingId, String type) {
        Document doc = new Document();
        doc.put("buildingId",buildingId);
        Document docs = mongoTemplate.getCollection(TableEnum.BUILDING_LOG_TABLE.getName()).find(doc).first();
        Map map = new HashMap();
        if("nameIcon".equals(type)){
            map.put("currentName",docs.get("currentName"));
            map.put("lastName",docs.get("lastName"));
        }
        System.out.println(map);
        return ApiResult.success(map);
    }

    @Override
    public ApiResult<List<Document>> getMergeListByToId(String id) {
        BusBuilding busBuilding = busBuildingDao.selectById(id,BusBuilding.class);
        String[] guidList = busBuilding.getOriginIdSet();
        Document queryCondition = new Document();
        queryCondition.put("guid", new BasicDBObject("$in", guidList));
        FindIterable<Document> list = mongoTemplate.getCollection(TableEnum.COMMUNITY_BUILDING_TABLE.getName()).find(queryCondition);
        List<Document> listVo = new ArrayList<>();
        for (Document document : list) {
            document.put("id",document.getObjectId("_id").toString());
            listVo.add(document);
        }
        return ApiResult.success(listVo);
    }

    /**
     * 根据楼层数获取建筑类别
     *
     * @param floorNum
     * @param dictMap
     * @return
     */
    public String formatBuildingType(int floorNum, HashMap<String, String> dictMap) {
        if (floorNum < 5) {
            return dictMap.get("低层");
        }
        if (floorNum < 8) {
            return dictMap.get("多层");
        }
        if (floorNum < 19) {
            return dictMap.get("小高层");
        }
        if (floorNum < 36) {
            return dictMap.get("高层");
        }
        return dictMap.get("超高层");
    }
}
