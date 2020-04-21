package com.hifo.dataoperation.service.avgprice;

import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dto.BusAvgPriceMgEtyDto;
import com.hifo.dataoperation.dto.BusCaseInfoEtyDto;
import com.hifo.dataoperation.entity.mongo.BusCaseInfoEty;
import com.hifo.dataoperation.pagination.Pagination;

/**
 * 案例信息serivce接口
 * @author weisibin
 * @date 2020年4月14日11:24:40
 */
@SuppressWarnings("rawtypes")
public interface BusCaseInfoService {
	/**
	 * 根据楼盘均价查询已用案例集合
	 * @param ba 单个实例参数
	 * @return 返回状态/数据
	 */
	ApiResult usedCaseList(BusAvgPriceMgEtyDto ba);
	
	/**
	 * 根据楼盘均价id分页查询此楼盘全部案例
	 * @param bcd 单个实例参数
	 * @return 返回状态/数据
	 */
	Pagination<BusCaseInfoEty> pageCaseList(BusCaseInfoEtyDto bcd, Pagination page);
}
