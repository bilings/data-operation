package com.hifo.dataoperation.controller;

import com.hifo.dataoperation.base.BasicController;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.intercept.RequiredPermission;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.estate.BusEstateAgentService;
import com.hifo.dataoperation.service.estate.BusEstateService;
import com.hifo.dataoperation.vo.BusEstateAgentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 合并推荐控制类
 *
 * @Author: xmw
 * @Date: 2019/5/24 15:13
 */
@Api(tags = "待建任务接口")
@RequestMapping("/agent")
@RestController
public class BusEstateAgentController extends BasicController {

    @Autowired
    private BusEstateService busEstateService;
    @Autowired
    private BusEstateAgentService busEstateAgentService;

    /**
     * 查询楼盘列表
     *
     * @param busEstate 查询条件
     * @return JSON String
     */
    @ApiOperation(value = "查询楼盘列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId", value = "城市id", paramType = "query", dataType = "String", example = "2"),
            @ApiImplicitParam(name = "cityName", value = "城市名称", paramType = "query", dataType = "String", example = "重庆市"),
            @ApiImplicitParam(name = "districtId", value = "行政区id", paramType = "query", dataType = "String", example = "3"),
            @ApiImplicitParam(name = "name", value = "楼盘名称或地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "createName", value = "维护人", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "visibility", value = "楼盘状态", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", dataType = "int", example = "1", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int", example = "5", required = true)
    })
    @GetMapping("/list")
    @RequiredPermission("401")
    public String list(Pagination pages, @ApiIgnore BusEstate busEstate) {
        // 分页数据
        var page = busEstateService.findByPage(busEstate, pages);
        return pageData(page.getTotal(), page.getRecords());
    }

    /**
     * 合并楼盘
     *
     * @param fromIds 被合并楼盘的id集合，以英文逗号,分隔
     * @param toId    合并至楼盘的id
     * @return ApiResult
     */
    @ApiOperation(value = "合并楼盘")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromIds", value = "被合并楼盘的id集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true),
            @ApiImplicitParam(name = "toId", value = "合并至楼盘的id", paramType = "query", dataType = "String", example = "1", required = true)
    })
    @PutMapping("/merge")
    @RequiredPermission("401")
    public ApiResult merge(String[] fromIds, String toId) {
        return busEstateService.merge(fromIds, toId);
    }

    /**
     * 设置删除
     *
     * @param ids id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "设置删除")
    @ApiImplicitParam(name = "ids", value = "楼盘编号集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    @PutMapping("/delete")
    @RequiredPermission("401")
    public ApiResult delete(String[] ids) {
        return busEstateService.delete(ids);
    }

    /**
     * 新建楼盘（修改待建状态）
     *
     * @param ids id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "新建楼盘")
    @ApiImplicitParam(name = "ids", value = "楼盘编号集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    @PutMapping("/agent")
    @RequiredPermission("401")
    public ApiResult agent(String[] ids) {
        return busEstateService.agent(ids);
    }

    /**
     * 设置是否推荐
     *
     * @param ids         id集合，以英文逗号,分隔
     * @param isRecommend 是否推荐
     * @return ApiResult
     */
    @ApiOperation(value = "设置是否推荐")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "推荐编号集合，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true),
            @ApiImplicitParam(name = "isRecommend", value = "是否推荐：是1否0", paramType = "query", dataType = "int", example = "1", required = true)
    })
    @PutMapping("/recommend")
    @RequiredPermission("401")
    public ApiResult recommend(String[] ids, Integer isRecommend) {
        return busEstateAgentService.recommend(ids, isRecommend);
    }

    /**
     * 查询合并推荐楼盘列表
     *
     * @param agentVO 查询条件
     * @return JSON String
     */
    @ApiOperation(value = "查询合并推荐楼盘列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityId", value = "城市id", paramType = "query", dataType = "String", example = "2"),
            @ApiImplicitParam(name = "fromName", value = "楼盘名称或地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fromDistrictId", value = "行政区id", paramType = "query", dataType = "String", example = "3"),
            @ApiImplicitParam(name = "isAgent", value = "待处理数据源", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "nameScore", value = "名称推荐分>=", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "addrScore", value = "地址推荐分>=", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "coordinateScore", value = "坐标推荐分>=", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "isRecommend", value = "是否推荐：是1否0", paramType = "query", dataType = "int", example = "1"),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "query", dataType = "int", example = "1", required = true),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int", example = "5", required = true)
    })
    @GetMapping("/lists")
    @RequiredPermission("402")
    public String lists(Pagination pages, @ApiIgnore BusEstateAgentVO agentVO) {
        // 分页数据
        var page = busEstateAgentService.findByPage(agentVO, pages);
        return pageData(page.getTotal(), page.getRecords());
    }

    /**
     * 批量合并楼盘
     *
     * @param ids 被合并楼盘的id集合，以英文逗号,分隔
     * @return ApiResult
     */
    @ApiOperation(value = "批量合并楼盘")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "被合并楼盘的id集合 fromId_toId，以英文逗号,分隔", paramType = "query", dataType = "String", example = "1,2", required = true)
    })
    @PutMapping("/merges")
    @RequiredPermission("402")
    public ApiResult merge(String[] ids) {
        return busEstateAgentService.merges(ids);
    }
}
