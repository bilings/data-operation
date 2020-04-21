package com.hifo.dataoperation.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.hifo.dataoperation.entity.mongo.BusWaitMergeEty;

/**
 * 待建合并数据访问层
 * @author weisibin
 * @date 2020年4月17日10:52:22
 */
@Repository
public class BusWaitMergeDao extends MongodbBasicDao<BusWaitMergeEty>{

	@Override
	public Class<BusWaitMergeEty> getEntityClass() {
		return BusWaitMergeEty.class;
	}

	@Override
	public String getCollectionName() {
		return "warehouse_community_waitmerge";
	}
	
	/**
	 * 根据wId参数判断数据是否存在
	 * @param wId 参数
	 * @return boolean true：存在 ，false： 不存在
	 */
	public boolean existWId(String wId){
	    long count = mongoTemplate.count(new Query(Criteria.where("wId").is(wId)),this.getEntityClass());
		if(count > 0){
			return true;
		}
		return false;
	}

}
