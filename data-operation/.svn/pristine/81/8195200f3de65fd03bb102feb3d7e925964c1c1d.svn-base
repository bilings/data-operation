package com.hifo.dataoperation.service.estate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.*;
import com.hifo.dataoperation.pagination.Pagination;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 楼盘Service接口
 *
 * @author xmw
 * @date 2019/4/25 16:21
 */
public interface BusEstateService extends IService<BusEstate> {

    /**
     * 新增或更新楼盘
     *
     * @param busEstate
     * @return
     */
    ApiResult update(BusEstate busEstate);

    /**
     * 删除楼盘
     *
     * @param ids
     * @return
     */
    ApiResult delete(String[] ids);

    /**
     * 设置可见性
     *
     * @param ids
     * @param visibility
     * @return
     */
    ApiResult visibility(String[] ids, String visibility);

    /**
     * 合并楼盘
     *
     * @param fromIds
     * @param toId
     * @return
     */
    ApiResult merge(String[] fromIds, String toId);

    /**
     * 取消合并楼盘
     *
     * @param fromId
     * @param toId
     * @return
     */
    ApiResult undoMerge(String fromId, String toId);

    /**
     * 虚拟化楼盘
     *
     * @param fromIds
     * @param toId
     * @return
     */
    ApiResult virtual(String[] fromIds, String toId);

    /**
     * 取消虚拟化楼盘
     *
     * @param fromId
     * @param type
     * @return
     */
    ApiResult undoVirtual(String type,String fromId);

    /**
     * 设置是否可估
     *
     * @param ids
     * @param canEvaluate
     * @return
     */
    ApiResult evaluate(String[] ids, String canEvaluate);

    /**
     * 分页查询楼盘列表
     *
     * @param busEstate
     * @param page
     * @return
     */
    Pagination<BusEstate> findByPage(BusEstate busEstate, Pagination page);

    /**
     * 合并至该楼盘的记录列表
     *
     * @param id
     * @return
     */
    ApiResult<List<Document>> getMergeListByToId(String id);

    /**
     * 楼盘虚拟记录虚拟至该楼盘的
     *
     * @param id
     * @return
     */
    ApiResult<List<BusEstate>> getVirtualListByFromId(String id);

    /**
     * 楼盘虚拟记录虚拟到其他楼盘的
     *
     * @param id
     * @return
     */
    ApiResult<List<BusEstate>> getVirtualListByToId(String id);

    /**
     * 更新已建楼栋
     *
     * @param id
     */
    void updateBuildingNo(String id);

    /**
     * 更新已建房号数
     *
     * @param id
     */
    void updateRoomNo(String id);

    /**
     * 条件查询列表
     *
     * @param busEstate
     * @return
     */
    List<BusEstate> queryByParam(BusEstate busEstate);
    /**
     * 批量导入楼盘信息
     *
     * @param file
     * @return
     * @throws Exception
     */
    String importEstate(MultipartFile file) throws Exception;

    /**
     * 设置代建属性未非代建（新建）
     *
     * @param ids
     * @return
     */
    ApiResult agent(String[] ids);

    /**
     * 楼盘详细信息
     *
     * @param id
     * @return
     */
    BusEstate getDetailById(String id);

    /**
     * 修改经纬度
     * @param busEstate
     * @return
     */
    ApiResult updatePosition(BusEstate busEstate);

    /**
     * 初始化测试数据
     */
    String init();

    /**
     * 获取楼盘单个字段历史维护记录
     * @param communityId
     * @param type
     * @return
     */
    ApiResult<Map> log(String communityId, String type);
}
