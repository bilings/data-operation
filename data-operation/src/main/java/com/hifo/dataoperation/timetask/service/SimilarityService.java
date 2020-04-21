package com.hifo.dataoperation.timetask.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.hifo.dataoperation.dao.BuiltEstateDao;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.dao.BusWaitMergeDao;
import com.hifo.dataoperation.entity.mongo.BuiltEstate;
import com.hifo.dataoperation.timetask.config.ContrastConfig;
import com.hifo.dataoperation.timetask.config.QueryConfig;
import com.hifo.dataoperation.timetask.thread.QueryThread;
import com.hifo.dataoperation.util.ExceptionMsgUtil;
import com.hifo.dataoperation.util.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 对比楼盘数据任务service
 * @author weisibin
 * @date 2020年4月17日16:25:36
 */
@Slf4j
@Component
public class SimilarityService {
	
	@Autowired
	private BuiltEstateDao builtEstateDao;
	
	@Autowired
	private BusEstateDao busEstateDao;
	
	@Autowired
	private QueryConfig queryConfig;
	
	@Autowired
	private ContrastConfig contrastConfig;
	
	@Autowired
	private BusWaitMergeDao busWaitMergeDao;
	
	/**
	 * 执行任务方法入口
	 */
	public void processor(){
		try {
			log.info("开始执行对比数据的定时任务========================");
			//1获得城市列表
			
			//2城市多线程查询处理,暂时不分城市，全部
			
			//3数据多线程
			//参实体
			BuiltEstate be = this.getParam();
			//获取总页码
			Integer totalpage = this.getTotalPage(be, queryConfig.getPageSize());
			//获取查询query
			Query query = this.getQuery(be); 
			query.limit(queryConfig.getPageSize().intValue());
			//初始话页码
			Integer pageNo = 1;
			//分页查询多线程
			while(pageNo <= totalpage){
				log.info("开始第=====【{}】=====页",pageNo);
				//设置query的查询页码，skip相当于从那条记录开始
				query.skip(pageNo.longValue());
				//多线程处理
				queryConfig.exec(new QueryThread(builtEstateDao,busEstateDao,busWaitMergeDao,contrastConfig,query));
				//页码自增
				pageNo ++;
				//每页查询间隔时间
				Thread.sleep(queryConfig.getSpaceTime());
			}
			//等待全部线程处理完成后，打印日志
			//cd.wait();
			log.info("完成执行对比数据的定时任务========================");
		} catch (Exception e) {
			log.error("对比拉取数据任务发生异常：{}",ExceptionMsgUtil.getStackMsg(e));
		}	
	}
	
	
	/**
	 * 组装Query对象
	 * @param be 查询条件
	 * @param pageSize  每次查多少页
	 * @return Query
	 */
	private Query getQuery(BuiltEstate be) {
		DBObject dbObject = new BasicDBObject();
		// 楼盘id
		if (StringUtil.isNotEmpty(be.getId())) {
			dbObject.put("_id", be.getId()); 
		}
		// 城市id
		if (StringUtil.isNotEmpty(be.getCityId())) {
			dbObject.put("cityId", be.getCityId());
		}
		// 城市名
		if (StringUtil.isNotEmpty(be.getCityName())) {
			dbObject.put("cityName", be.getCityName());
		}
		// 区域id
		if (StringUtil.isNotEmpty(be.getDistrictId())) {
			dbObject.put("districtId", be.getDistrictId());
		}
		// 区域名
		if (StringUtil.isNotEmpty(be.getDistrictName())) {
			dbObject.put("districtName", be.getDistrictName());
		}
		// 是否已经合并
		if (StringUtil.isNotEmpty(be.getIsMerged())) {
			dbObject.put("isMerged", be.getIsMerged());
		}
		BasicDBObject fieldsObject = new BasicDBObject();
		
		//指定返回的字段
		fieldsObject.put("_id", true);
		fieldsObject.put("name", true);
		fieldsObject.put("cityId", true);
		fieldsObject.put("cityName", true);
		fieldsObject.put("districtId", true);
		fieldsObject.put("districtName", true);
		fieldsObject.put("address", true);
		fieldsObject.put("siteName", true);
		fieldsObject.put("caseNo", true);
		
		Query query = new BasicQuery(dbObject.toString(),fieldsObject.toJson());
		return query;
	}
	
	/**
	 * 获取参数实体
	 * @return BuiltEstate
	 */
	private BuiltEstate getParam(){
		BuiltEstate be = new BuiltEstate(); 
		//查询没有进行合并的数据
		be.setIsMerged(false);
		return be;
	}
	
	/**
	 * 获取分页总页数
	 * @param be 
	 * @param pageSize
	 * @return Integer 页总页数
	 */
	private Integer getTotalPage(BuiltEstate be,Integer pageSize){
		Map<String,Object> param = new HashMap<>();
		
		//查询没有进行合并的数据
		param.put("isMerged", be.getIsMerged());
		//获取总条数
		Long count = builtEstateDao.countByCondition(param);
		//返回总页数
		return (int) Math.ceil(count/pageSize);
	}
}
