package com.hifo.dataoperation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hifo.dataoperation.entity.coe.BusOrganizationCoetype;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.service.coe.BusOrganizationCoetypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 杨捷
 * @date 2019年5月24日
 * @description
 */
@Api(tags = "算法修正因素接口")
@RequiredPermission("604")
@RequestMapping("/coetype")
@RestController
public class BusOrganizationCoetypeController {

	@Autowired
	private BusOrganizationCoetypeService busOrganizationCoetypeService;

	@GetMapping("/get")
	@ApiOperation(value = "获取当前用户所属机构下的修正因素")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "administrativeId", required = true, value = "城市id", paramType = "query", dataType = "String") })
	@RequiredPermission("604")
	public ApiResult getCoetype(String administrativeId) {
		return ApiResult.success( busOrganizationCoetypeService.getCoeTypes(administrativeId));
	}

	@PostMapping("/save")
	@ApiOperation(value = "设置一个机构下的修正因素")
	@RequiredPermission("604")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coetypes", required = true, value = "系数类型   1、楼层系数； 2、朝向系数；3、建筑结构系数； 4、面积系数；5、景观系数，多个类型用逗号分隔", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "id", required = true, value = "id", paramType = "query", dataType = "int") })
	public ApiResult saveCoetype(@RequestBody BusOrganizationCoetype coeType) {
		boolean res = busOrganizationCoetypeService.setCoeType(coeType);

		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}
}
