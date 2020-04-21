package com.hifo.dataoperation.service.avgprice;

import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dto.BusAvgPriceMgEtyDto;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.mongo.BusAvgPriceMgEty;
import com.hifo.dataoperation.pagination.Pagination;

/**
 * 楼盘均价业务层接口
 * @author weisibin
 * @2020年4月8日17:43:36
 */
@SuppressWarnings("rawtypes")
public interface BusAvgPriceMgService{
	
	/**
     * 分页查询楼盘均价
     * @param BusAvgPriceMgEtyDto 条件参数dto
     * @param page 分页参数
     * @return Pagination<BusEstateAvgPriceEtyDto> 返回的分页数据
     */
	Pagination<BusAvgPriceMgEty> findByPage(BusAvgPriceMgEtyDto busAvgPriceEtyDto, Pagination page);
    
	/**
	 * 获取建筑分类枚举
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult buildingType();
	
	/**
	 * 通过楼盘获取建筑分类
	 * @param BusEstate be 查询参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult estateBuildingType(BusEstate be);
	
	/**
	 * 通过楼盘和建筑分类获取面积段
	 * @param BusEstate be 查询参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult areasSection(BusEstate be);
	
	/**
	 * 批量/单条-隐藏/显示
	 * @param ids 多个id
	 * @param ba 单个实例参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult hideOrShow(String[] ids,BusAvgPriceMgEtyDto ba);
	
	/**
	 * 新增均价
	 * @param ba 单个实例参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult addAvgPrice(BusAvgPriceMgEtyDto ba);
	
	/**
	 * 编辑人工均价
	 * @param ba 单个实例参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult editAvgPriceManual(BusAvgPriceMgEtyDto ba);
	
	/**
	 * 采用修正后的均价
	 * @param ba 单个实例参数
	 * @return ApiResult 返回状态/数据
	 */
	ApiResult applyPrice(BusAvgPriceMgEtyDto ba);
	
	
}
