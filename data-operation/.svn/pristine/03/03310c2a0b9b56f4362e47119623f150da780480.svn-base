package com.hifo.dataoperation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.dto.BusCaseInfoEtyDto;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.dto.BusAvgPriceMgEtyDto;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.avgprice.BusAvgPriceMgService;
import com.hifo.dataoperation.service.avgprice.BusCaseInfoService;

import lombok.var;

/**
 * 楼盘均价 控制层
 * @author weisibin
 * @date 2020年4月8日18:02:39
 */
@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/avgPrice")
public class BusAvgPriceMgControoler extends BasicController{
	
	@Autowired
    private BusAvgPriceMgService busAvgPriceMgService;
	
	@Autowired
    private BusCaseInfoService busCaseInfoService;
	
	/**
	 * 分页条件查询均价列表
	 * @param pages 分页参数
	 * @param ba 交互实例
	 * @return String 返回数据
	 */
	@GetMapping("/list")
    @RequiredPermission("300")
    public String list(Pagination pages, BusAvgPriceMgEtyDto ba) {
        var page = busAvgPriceMgService.findByPage(ba, pages);
        return pageData(page.getTotal(), page.getRecords());
    }
	
	/**
	 * 获取建筑分类
	 * @param busEstateAvgPriceEtyDto 交互实例
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/buildingType")
    public ApiResult buildingType() {
		return busAvgPriceMgService.buildingType();
    }
	
	/**
	 * 获取建筑分类
	 * @param busEstateAvgPriceEtyDto 交互实例
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/estateBuildingType")
    public ApiResult estateBuildingType(@RequestBody BusEstate be) {
		return busAvgPriceMgService.estateBuildingType(be);
    }
	
	/**
	 * 批量/单条-隐藏/显示
	 * @param ba 交互实例
	 * @return ApiResult 返回结果
	 */
	@PostMapping("/hideOrShow")
    public ApiResult hideOrShow(@RequestParam(value="ids[]") String[] ids,BusAvgPriceMgEtyDto ba) {
		return busAvgPriceMgService.hideOrShow(ids, ba);
    }
	
	/**
	 * 新增均价信息
	 * @param ba 交互实例
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/addAvgPrice")
    public ApiResult addAvgPrice(@RequestBody BusAvgPriceMgEtyDto ba) {
		return busAvgPriceMgService.addAvgPrice(ba);
    }
	
	/**
	 * 编辑人工均价
	 * @param ba 交互实例
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/editManualPrice")
    public ApiResult editAvgPriceManual(@RequestBody BusAvgPriceMgEtyDto ba) {
		return busAvgPriceMgService.editAvgPriceManual(ba);
    }
	
	/**
	 * 初始化已用案例的表格list
	 * @param ba 交互实例
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/usedCaseList")
    public ApiResult usedCaseList(@RequestBody BusAvgPriceMgEtyDto bc) {
		return busCaseInfoService.usedCaseList(bc);
    }
	
	/**
	 * 根据楼盘均价id分页查询此楼盘全部案例
	 * @param id 据楼盘均价id
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/pageCaseList")
	public String pageCaseList(BusCaseInfoEtyDto bcd,Pagination page) {
		var p = busCaseInfoService.pageCaseList(bcd,page);
		return pageData(p.getTotal(), p.getRecords());
	}
	
	/**
	 * 模拟修正均价计算接口,这里只是模拟，不是正式接口
	 * @param id 据楼盘均价id
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/calculateAvgPrice")
	public ApiResult calculateAvgPrice(@RequestBody List<BusAvgPriceMgEtyDto> bas) {
		return ApiResult.success("16874");
	}
	
	/**
	 * 根据楼盘均价id分页查询此楼盘全部案例
	 * @param id 据楼盘均价id
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/applyPrice")
	public ApiResult applyPrice(@RequestBody BusAvgPriceMgEtyDto ba) {
		return busAvgPriceMgService.applyPrice(ba);
	}
	
	/**
	 * 根据楼盘均价id分页查询此楼盘全部案例
	 * @param id 据楼盘均价id
	 * @return ApiResult 返回结果
	 */
	@RequestMapping("/areasSection")
	public ApiResult areasSection(@RequestBody BusEstate be) {
		return busAvgPriceMgService.areasSection(be);
	}
	
}
