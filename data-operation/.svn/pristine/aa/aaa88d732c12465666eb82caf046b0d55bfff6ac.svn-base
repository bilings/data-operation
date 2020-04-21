package com.hifo.dataoperation.controller;

import java.util.*;


import com.hifo.dataoperation.entity.coe.FloorCityConfig;
import com.hifo.dataoperation.entity.coe.FloorCoefficient;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.*;
import com.hifo.dataoperation.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hifo.dataoperation.entity.coe.BusCoeGroup;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.service.coe.exception.IllegalCoeItemException;
import com.hifo.dataoperation.util.TreeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(tags = "通用系数接口")
@RequiredPermission("603")
@RequestMapping("/coe")
@RestController
/**
 * 
 * @author 柏小龙
 * @date 2020年3月31日
 * @description
 */
public class CoeController {
	@Autowired
	private BusCoeGroupService busCoeGroupService;
	@Autowired
	private CoeService<FloorCoe, FloorCoeDetail> floorCoeServiceImpl;
	@Autowired
	private CoeService<OrientationCoe, OrientationCoeDetail> orientationCoeServiceImpl;
	@Autowired
	private CoeService<StructureCoe, StructureCoeDetail> structureCoeServiceImpl;
	@Autowired
	private CoeService<AreaCoe, AreaCoeDetail> areaCoeServiceImpl;
	@Autowired
	private CoeService<SceneryCoe, SceneryCoeDetail> sceneryCoeServiceImpl;
	@Autowired
	private BusCoeStructureService busCoeStructureService;
	@Autowired
	private FloorCityConfigService floorCityConfigService;
	@Autowired
	private FloorCoefficientService floorCoefficientService;

	@Autowired
	private BasicService basicService;



	@GetMapping("/group/getTree")
	@ApiOperation(value = "根据上级节点获取下一级通用系数分组树，如果pid为空则返回根节点")
	@RequiredPermission("603")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "coeType", required = true, value = "系数类型   1、楼层系数； 2、朝向系数；3、建筑结构系数； 4、面积系数", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "cityId", required = true, value = "城市对应id", paramType = "query", dataType = "int") })
	public ApiResult getGroupTree(String coeType, String cityId) {
		List<FloorCityConfig> list =floorCityConfigService.List();
		if (list != null && !list.isEmpty()) {
			return ApiResult.success(getTree(list).getChildren());
		}
		return ApiResult.success();
	}

	private TreeVO getTree(List<FloorCityConfig> list) {
		List<CoeGroupTree> treeList = convertTreeNode(list);
		TreeVO res = TreeUtils.getTreeByList(treeList);
		return res;
	}

	private List<CoeGroupTree> convertTreeNode(List<FloorCityConfig> list) {
		List<CoeGroupTree> res = new ArrayList<CoeGroupTree>();
		for (FloorCityConfig entity : list) {
			CoeGroupTree treeNode = new CoeGroupTree();
			treeNode.setId(entity.getId());
			treeNode.setParentId(entity.getPid());
			treeNode.setName(entity.getCityName());
			res.add(treeNode);
		}
		return res ;
	}

	@PostMapping("/group/add")
	@ApiOperation(value = "新增通用系数分组")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cityId", required = true, value = "城市对应系数组的id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "cityName", required = true, value = "所属城市名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "administrativeId", required = false, value = "行政区id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "areaId", required = false, value = "对应片区id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "coeTypeSelect", required = true, value = "系数类型； 1：城市系数:0：通用系数", paramType = "query", dataType = "int")

	})
	@RequiredPermission("603")
	public ApiResult addCoeGroup(@RequestBody FloorCityConfigVO configVO) {
		try {
			//通用系数
			if(configVO.getCoeTypeSelect().equals("0")){
				FloorCityConfig c=new FloorCityConfig();
				Query query=new Query();
				Criteria criteria=new Criteria();
				criteria.and("type").is("0");
				query.addCriteria(criteria);

				FloorCityConfig oldConfig=floorCityConfigService.findByQuery(query);

				if(oldConfig!=null){
					ApiResult.success("通用系数已添加，不能重复添加");
				}
				String loginUser=basicService.getLoginEmployee().getName();


				c.setType(configVO.getCoeTypeSelect());
				c.setCreateTime(new Date());
				c.setCreateUser(loginUser);
				c.setCityName("通用系数");
				floorCityConfigService.save(c);
			}
			else{
				floorCityConfigService.addConfig(configVO);
			}

		} catch (Exception e) {
			return ApiResult.failed(e.getMessage());
		}
		return ApiResult.success();
	}

	@GetMapping("/structure/getTree")
	@ApiOperation(value = "获取一个分组下的通用系数结构树")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "configId", required = true, value = "配置ID", paramType = "query", dataType = "String")
	})
	@RequiredPermission("603")
	public ApiResult getStructureTree(String configId) {
		FloorCoefficient res = floorCoefficientService.selectByCid(configId);
		if (res != null) {
			return ApiResult.success(res);
		}

		return ApiResult.success();
	}
	
	private List<CoeStructureRes> setDeleteAble(List<CoeStructureRes> oldList,Long groupId){
		if(oldList!=null) {
			List<CoeStructureRes> res= new ArrayList<CoeStructureRes>();
			if(oldList.size()>1) {
				Map<Long,List<CoeStructureRes>> childMap=new HashMap<Long,List<CoeStructureRes>>();
				for (CoeStructureRes coeStructureRes : oldList) {
					Long parent = coeStructureRes.getParent();
					List<CoeStructureRes> tempChildList=childMap.get(parent);
					if(tempChildList==null) {
						tempChildList=new ArrayList<>();
					}
					tempChildList.add(coeStructureRes);
					childMap.put(parent, tempChildList);
				}
				for (CoeStructureRes coeStructureRes : oldList) {
					Long key = coeStructureRes.getKey();
					if(childMap.get(key)==null) {
						coeStructureRes.setDeleteAble(true);
					}else {
						coeStructureRes.setDeleteAble(false);
					}
					res.add(coeStructureRes);
				}
			}else if(oldList.size()==1) {
				CoeStructureRes coeStructureRes = oldList.get(0);
				//如果是顶级系数组（城市系数组）下的顶级节点，则不能删除
				if(isCityRoot(groupId)) {
					coeStructureRes.setDeleteAble(false);
				}else {
					coeStructureRes.setDeleteAble(true);
				}
				res.add(coeStructureRes);
			}
			return res;

		}
		return null;
	}
	
	private boolean isCityRoot(Long groupId) {
		
		BusCoeGroup group=busCoeGroupService.getById(groupId);
		if(group.getpId()==null) {
			return true;
		}
		
		return false;
	}
	
	
	@GetMapping("/structure/get")
	@ApiOperation(value = "根据结构id获取一个通用系数结构")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "结构id", paramType = "query", dataType = "int")
	})
	@RequiredPermission("603")
	public ApiResult getStructureById(Long id) {
		CoeStructureRes res = busCoeStructureService.getStructureById(id);
		if (res != null ) {
			return ApiResult.success(res);
		}

		return ApiResult.failed();
	}
	
	
	

	@PostMapping("/structure/add")
	@ApiOperation(value = "新增一个通用系数结构")
	@RequiredPermission("603")
	public ApiResult addCoeStructure(@ApiParam(name = "系数信息", value = "json", required = true) @RequestBody  FloorCoefficient param) {
		try {
			FloorCoefficient oldEntity=floorCoefficientService.selectByCid(param.getCid());
			//如果该城市 存在数据 删掉后再添加
			if (oldEntity!=null){
				floorCoefficientService.delete(oldEntity.getId());
			}
			floorCoefficientService.save(param);
		} catch (Exception e) {
			return ApiResult.failed(e.getMessage());
		}
		return ApiResult.success();
	}

	@PutMapping("/structure/edit")
	@ApiOperation(value = "编辑一个通用系数结构")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", required = true, value = "要编辑的系数结构的id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "pid", required = true, value = "父级结构id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "conditionType", required = true, value = "条件类型  1:整体区域,2:物业品质，3:有无电梯，4:建筑类别，5:建成年代，6:主力面积", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "coeType", required = true, value = "系数类型  1：楼层系数:2：朝向系数:3：建筑结构系数:4：面积系数", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "groupId", required = true, value = "所属分组id", paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "dictionaryIds", required = false, value = " 条件项对应的字典表id集合，用逗号分隔，没有条件项可传null", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dictionaryNames", required = false, value = " 条件项对名称集合，用逗号分隔，没有条件项可传null", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "conditionRangeStart", required = false, value = "条件范围起始值", paramType = "query", dataType = "float"),
			@ApiImplicitParam(name = "conditionRangeEnd", required = false, value = "条件范围结束值", paramType = "query", dataType = "float")
	})
	@RequiredPermission("603")
	public ApiResult editCoeStructure(@RequestBody CoeStructureParam param) {
		try {
			busCoeStructureService.editCoeStructure(param);
		} catch (IllegalCoeItemException e) {
			return ApiResult.failed(e.getMessage());
		}
		return ApiResult.success();
	}

	@DeleteMapping("/structure/delete")
	@ApiOperation(value = "删除一个城市系数结构")
	@RequiredPermission("603")
	public ApiResult deleteCoeStructure(String id) {
		try {
			String[] a={id};
			floorCityConfigService.delete(a);
			FloorCoefficient ffEntity=floorCoefficientService.selectByCid(id);
			//删除城市配置 同时删除系数配置
			if(ffEntity!=null){
				floorCoefficientService.delete(ffEntity.getId());
			}
		}
		catch (Exception e){
			return ApiResult.failed(e.getMessage());
		}
		return ApiResult.success();
	}

	@PutMapping("/detail/editOrientation")
	@ApiOperation(value = "编辑朝向系数")
	@RequiredPermission("603")
	public ApiResult editOrientationCoe(
			@ApiParam(name = "编辑朝向系数", value = "系数的结果集，以json格式保存", required = true) @RequestBody OrientationCoe coe) {
		boolean res = orientationCoeServiceImpl.saveCoe(coe);
		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}

	@PutMapping("/detail/editFloorCoe")
	@ApiOperation(value = "编辑楼层系数")
	@RequiredPermission("603")
	public ApiResult editFloorCoe(
			@ApiParam(name = "编辑楼层系数", value = "系数的结果集，以json格式保存", required = true) @RequestBody FloorCoe coe) {
		boolean res = floorCoeServiceImpl.saveCoe(coe);
		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}

	@PutMapping("/detail/editStructureCoe")
	@ApiOperation(value = "编辑建筑结构系数")
	@RequiredPermission("603")
	public ApiResult editStructureCoe(
			@ApiParam(name = "编辑建筑结构系数", value = "系数的结果集，以json格式保存", required = true) @RequestBody StructureCoe coe) {
		boolean res = structureCoeServiceImpl.saveCoe(coe);
		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}

	@PutMapping("/detail/editAreaCoe")
	@ApiOperation(value = "编辑面积系数")
	@RequiredPermission("603")
	public ApiResult editAreaCoe(
			@ApiParam(name = "编辑面积系数", value = "系数的结果集，以json格式保存", required = true) @RequestBody AreaCoe coe) {
		boolean res = areaCoeServiceImpl.saveCoe(coe);
		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}

	@PutMapping("/detail/editSceneryCoe")
	@ApiOperation(value = "编辑景观系数")
	@RequiredPermission("603")
	public ApiResult editSceneryCoe(
			@ApiParam(name = "编辑景观系数", value = "系数的结果集，以json格式保存", required = true) @RequestBody SceneryCoe coe) {
		boolean res = sceneryCoeServiceImpl.saveCoe(coe);
		if (res) {
			return ApiResult.success();
		}
		return ApiResult.failed();
	}
	
	@GetMapping("/detail/getOrientationCoe")
	@ApiOperation(value = "获取朝向系数")
	@RequiredPermission("603")
	public ApiResult getOrientationCoe(
			@ApiParam(name = "structureId", value = "对应结构的id", required = true) Long structureId) {
		OrientationCoe res = orientationCoeServiceImpl.getCoeByStructureId(structureId);
		if (res!=null) {
			return ApiResult.success(res);
		}
		return ApiResult.failed();
	}
	
	@GetMapping("/detail/getFloorCoe")
	@ApiOperation(value = "获取楼层系数")
	@RequiredPermission("603")
	public ApiResult getFloorCoe(
			@ApiParam(name = "structureId", value = "对应结构的id", required = true) Long structureId) {
		FloorCoe res = floorCoeServiceImpl.getCoeByStructureId(structureId);
		if (res!=null) {
			return ApiResult.success(fillDto(res));
		}
		return ApiResult.failed();
	}
	
	private FloorCoe fillDto(FloorCoe coe) {
		List<FloorCoeGroupByTotalNo> groupLit =coe.getGroups();
		List<FloorCoeGroupByTotalNo> resList = new ArrayList<>();
		int size = groupLit.size();
		for (FloorCoeGroupByTotalNo group : groupLit) {
			List<FloorCoeDetail> detailList = group.getDetailList();
			int detailSize = detailList.size();
			if(detailSize<size) {
				for(int i=0;i<size-detailSize;i++) {
					FloorCoeDetail temp=new FloorCoeDetail();
					detailList.add(temp);
				}
				group.setDetailList(detailList);
			}
			resList.add(group);
		}
		coe.setGroups(resList);
		return coe;
	}
	
	@GetMapping("/detail/getStructureCoe")
	@ApiOperation(value = "获取建筑结构系数")
	@RequiredPermission("603")
	public ApiResult getStructureCoe(
			@ApiParam(name = "structureId", value = "对应结构的id", required = true) Long structureId) {
		StructureCoe res = structureCoeServiceImpl.getCoeByStructureId(structureId);
		if (res!=null) {
			return ApiResult.success(res);
		}
		return ApiResult.failed();
	}
	
	@GetMapping("/detail/getAreaCoe")
	@ApiOperation(value = "获取面积系数")
	@RequiredPermission("603")
	public ApiResult getAreaCoe(
			@ApiParam(name = "structureId", value = "对应结构的id", required = true) Long structureId) {
		AreaCoe res = areaCoeServiceImpl.getCoeByStructureId(structureId);
		if (res!=null) {
			return ApiResult.success(res);
		}
		return ApiResult.failed();
	}
	
	@GetMapping("/detail/getSceneryCoe")
	@ApiOperation(value = "获取景观系数")
	@RequiredPermission("603")
	public ApiResult getSceneryCoe(
			@ApiParam(name = "structureId", value = "对应结构的id", required = true) Long structureId) {
		SceneryCoe res = sceneryCoeServiceImpl.getCoeByStructureId(structureId);
		if (res!=null) {
			return ApiResult.success(res);
		}
		return ApiResult.failed();
	}
	
}
