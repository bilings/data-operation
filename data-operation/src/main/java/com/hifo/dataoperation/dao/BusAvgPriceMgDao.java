package com.hifo.dataoperation.dao;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hifo.dataoperation.entity.mongo.BusAvgPriceMgEty;

/**
 * 楼盘均价
 * 
 * @author weisibin
 * @date 2020年4月8日11:04:33
 */
@Repository
public class BusAvgPriceMgDao extends MongodbBasicDao<BusAvgPriceMgEty> {

	@Override
	public Class<BusAvgPriceMgEty> getEntityClass() {
		return BusAvgPriceMgEty.class;
	}

	@Override
	public String getCollectionName() {
		return "warehouse_community_price1";
	}

	/**
	 * 根据ids进行批量修改
	 * 
	 * @param ids
	 *            参数
	 * @param ba
	 *            修改实例
	 */
	public void updateByIds(String[] ids, BusAvgPriceMgEty ba) {
		Query query = new Query(Criteria.where("id").in(ids));
		Update update = new Update();
		String str = gson.toJson(ba);
		JSONObject jQuery = JSON.parseObject(str);
		jQuery.forEach((key, value) -> {
			// 因为id相当于传统数据库中的主键，这里使用时就不支持更新，所以需要剔除掉
			if (!key.equals("id") && !key.equals("opsition")) {
				update.set(key, value);
			}
		});
		this.mongoTemplate.updateMulti(query, update, ba.getClass());
	}
}
