package com.hifo.dataoperation.service.estate.impl;

import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BuiltEstateDao;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.dao.BusWaitMergeDao;
import com.hifo.dataoperation.dto.BusWaitMergeEtyDto;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.mongo.BuiltEstate;
import com.hifo.dataoperation.entity.mongo.BusWaitMergeEty;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.estate.BuiltService;
import com.hifo.dataoperation.util.StringUtil;
import com.hifo.dataoperation.util.TableEnum;
import com.mongodb.client.FindIterable;

import lombok.var;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@SuppressWarnings({ "deprecation", "rawtypes" })
@Service
public class BuiltServiceImpl implements BuiltService {
    @Autowired
    private BuiltEstateDao builtEstateDao;
    @Autowired
    private BusEstateDao busEstateDao;

    @Autowired
    private BusWaitMergeDao busWaitMergeDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Pagination<BuiltEstate> findByPage(BuiltEstate busEstate, Pagination pages) {
        Query query = findListQuery(busEstate, pages);
        Pagination<BuiltEstate> busEstatesList = builtEstateDao.getPageLists(pages.getPage(),pages.getLimit(),query);
        return  busEstatesList;
    }

    /**
     * 删除待建楼盘
     * @param busEstates
     * @return
     */
    @Override
    public ApiResult add(List<BusEstate> busEstates) {
        for (BusEstate estate : busEstates) {
            Document document = mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).find(new Document("cityId",estate.getCityId()).append("districtId",estate.getDistrictId()).append("name",estate.getName())).first();
           if(document !=null){
                return ApiResult.failed("添加失败[ "+estate.getName()+"]已存在");
            }
            String id = new ObjectId().toString();
            estate.setId(id);
            estate.setIsDeleted(false);
            busEstateDao.insert(estate);
            mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName()).updateOne(new Document("_id",new ObjectId(estate.getId())),new Document("$set",new Document("isMerged",true).append("mergeId",id)));
        }
        return ApiResult.success("添加成功");
    }

    @Override
    public ApiResult delete(List<BuiltEstate> busEstates) {
        for (BuiltEstate estate : busEstates) {
            builtEstateDao.deleteById(estate.getId());
        }
        return ApiResult.success("删除成功");
    }

    /**
     * 合并推荐-合并楼盘
     */
    @Override
    public ApiResult merges(List<Map<String,String>> bl) {
    	for (Map<String, String> b : bl) {
    		 this.merge(b.get("waitId"),b.get("targetId"));
		}
        return  ApiResult.success("合并成功");
    }

    /**
     * 格式化列表查询条件
     * @param busEstate
     * @param page
     * @return
     */
    public static Query findListQuery(BuiltEstate busEstate, Pagination page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(busEstate.getCityId())) {
            criteria.and("cityId").is(busEstate.getCityId());
        }
        if (StringUtils.isNotBlank(busEstate.getDistrictId())) {
            criteria.and("districtId").is(busEstate.getDistrictId());
        }
        if (StringUtil.isNotEmpty(busEstate.getCommunityName())) {
            Pattern pattern= Pattern.compile("^.*"+busEstate.getCommunityName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.orOperator(Criteria.where("communityName").regex(pattern),
                    Criteria.where("otherName").regex(pattern),
                    Criteria.where("address").regex(pattern)
            );
        }
        criteria.and("isMerged").is(false);
        query.addCriteria(criteria);
        if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime")));
        }
        return query;
    }

    
	@Override
	public Pagination<BusWaitMergeEty> findWaitMergeByPage(BusWaitMergeEtyDto bwd, Pagination pages) {
		Query query = findWaitMergeQuery(bwd, pages);
		Pagination<BusWaitMergeEty> bwl = busWaitMergeDao.getPageLists(pages.getPage(), pages.getLimit(),
				query);
		return bwl;
	}
	
	
	/**
     * 格式化查询待合并数据的列表查询条件
     * @param bwd 查询参数
     * @param page 分页参数
     * @return Query 返回对象
     */
	private Query findWaitMergeQuery(BusWaitMergeEtyDto bwd, Pagination page) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(bwd.getCityId())) {
            criteria.and("tCityId").is(bwd.getCityId());
        }
        if (StringUtils.isNotBlank(bwd.getDistrictId())) {
            criteria.and("tDistrictId").is(bwd.getDistrictId());
        }
        if (null != bwd.getNameScore()) {
            criteria.and("nameScore").gte(bwd.getNameScore());
        }
        if (null != bwd.getAddressScore()) {
            criteria.and("addressScore").gte(bwd.getAddressScore());
        }
        criteria.and("isMerged").is("0");
        query.addCriteria(criteria);
        if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "nameScore")));
        }
        return query;
    }
	
	
	/**
	 * 合并推荐-合并楼盘
	 * @param fromId 源库楼盘id
	 * @param toId 标准库楼盘id
	 */
	private void merge(String formId, String toId) {
		// 1. 获取源楼盘, 目标楼盘
		var to = busEstateDao.selectById(toId, BusEstate.class);

		Set<String> otherName = new HashSet<>(to.getOtherName());
		Set<String> otherAddress = new HashSet<>(to.getOtherAddress());

		// 不能合并自身楼盘
		if (formId.equals(toId)) {
			return;
		}
		// 获取源库的信息
		var fromCommunity = builtEstateDao.selectById(formId, BuiltEstate.class);

		String guid = fromCommunity.getGuid();
		// 从源库中取出
		Document standardCommunity = mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName())
				.find(new Document("guid", guid)).first();
		// 找出标准楼盘下的案例，将该案例commmunityId 改为合并目标楼盘的ID
		FindIterable<Document> caseInfo = mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName())
				.find(new Document("communityId", standardCommunity.getObjectId("_id").toString()));
		for (Document document : caseInfo) {
			mongoTemplate.getCollection(TableEnum.CASEINFO_STANDARD_TABLE.getName()).updateOne(
					new Document("_id", document.getObjectId("_id")),
					new Document("$set", new Document("communityId", toId)));
		}
		// 把guid放到标准库中
		List<String> guidToList = to.getGuidList();
		if (guidToList != null) {
			guidToList.add(guid);
		}
		// 添加名字到别名中
		otherName.add(fromCommunity.getName());
		// 添加地址到其他地址
		otherAddress.add(fromCommunity.getAddress());
		// 修改to楼盘的guidList
		mongoTemplate.getCollection(TableEnum.COMMUNITY_SERVICE_TABLE.getName()).updateOne(
				new Document("_id", new ObjectId(toId)), new Document("$set", new Document("guidList", guidToList)
						.append("otherName", otherName).append("otherAddress", otherAddress)));
		//合并完成后修改源庫的合并状态
		mongoTemplate.getCollection(TableEnum.COMMUNITY_STANDARD_TABLE.getName()).updateOne(
				new Document("_id", new ObjectId(formId)), new Document("$set", new Document("isMerged", true)));
	}
}
