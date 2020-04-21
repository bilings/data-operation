package com.hifo.dataoperation.service.avgprice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dao.BusCaseInfoDao;
import com.hifo.dataoperation.dao.BusEstateDao;
import com.hifo.dataoperation.dao.BusAvgPriceMgDao;
import com.hifo.dataoperation.dto.BusAvgPriceMgEtyDto;
import com.hifo.dataoperation.dto.BusCaseInfoEtyDto;
import com.hifo.dataoperation.entity.mongo.BusCaseInfoEty;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.mongo.BusAvgPriceMgEty;
import com.hifo.dataoperation.service.avgprice.BusCaseInfoService;
import com.hifo.dataoperation.util.StringUtil;

/**
 * 均价案例Service
 * @author weisibin
 * @date 2020年4月14日14:31:38
 */
@Service
@SuppressWarnings({"rawtypes","deprecation"})
public class BusCaseInfoServiceImpl implements BusCaseInfoService{

	@Autowired
	private BusCaseInfoDao busCaseInfoDao;
	
	@Autowired
	private BusAvgPriceMgDao busAvgPriceMgDao;
	
	@Autowired
	private BusEstateDao busEstateDao;
	
	@Override
	public ApiResult usedCaseList(BusAvgPriceMgEtyDto bad) {
		//获得使用的案例id
		BusAvgPriceMgEty ba = busAvgPriceMgDao.selectById(bad.getId(), BusAvgPriceMgEty.class);
		if(null == ba){
			return ApiResult.failed("查询均价为空");
		}
		//跟俊查询的案例id获取案例信息
		List<BusCaseInfoEty> bcl = busCaseInfoDao.selectByIds(ba.getUsedCaseIdList().toArray());
		if(bcl.isEmpty()){
			return ApiResult.failed("查询案例为空");
		}
		return ApiResult.success(bcl);
	}

	
	
	@Override
	public Pagination<BusCaseInfoEty> pageCaseList(BusCaseInfoEtyDto bcd, Pagination page) {
		//通过均价id获得楼盘id
		BusAvgPriceMgEty ba = busAvgPriceMgDao.selectById(bcd.getAvgPriceId(), BusAvgPriceMgEty.class);
		
		//通过楼盘id获得楼盘信息中的  communityUrl， 这个是唯一的，可以根据communityUrl获得此楼盘下的全部案例
		BusEstate be = busEstateDao.selectById(ba.getCommunityId(), BusEstate.class);
		
		//设置参数
		bcd.setCommunityUrl(be.getCommunityUrl());
		Pagination<BusCaseInfoEty> p_bce = busCaseInfoDao.getPageLists(page.getPage(),
				page.getLimit(), this.findListQuery(bcd, page));
		
		return p_bce;
	}
	
	
	/**
	 * 格式化列表查询条件
	 * 
	 * @param apd
	 *            案例参数实例
	 * @param page
	 *            分页参数
	 * @return Query
	 */
	private Query findListQuery(BusCaseInfoEtyDto bcd, Pagination page) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		// 社区url
		if(StringUtil.isNotEmpty(bcd.getCommunityUrl())){
			criteria.and("communityUrl").is(bcd.getCommunityUrl());
		}
		// 面积区域
		if (StringUtil.isNotEmpty(bcd.getAreaMin()) && StringUtil.isNotEmpty(bcd.getAreaMax())) {
			criteria.and("buildArea").gte(Double.parseDouble(bcd.getAreaMin())).lte(Double.parseDouble(bcd.getAreaMax()));
		}
		// 案例时间区域
		if (StringUtil.isNotEmpty(bcd.getCaseDateStart()) && StringUtil.isNotEmpty(bcd.getCaseDateEnd())) {
			criteria.and("caseMonth").gte(Long.parseLong(bcd.getCaseDateStart())).lte(Long.parseLong(bcd.getCaseDateEnd()));
		}
		query.addCriteria(criteria);
		if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
			query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "caseDate")));
		}
		return query;
	}
}
