package com.hifo.dataoperation.service.estate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.BusBuilding;
import com.hifo.dataoperation.entity.BusRoom;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.vo.BusRoomVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 房号
 *
 * @author jinzhichen
 * @date 2019/4/23 15:22
 */
public interface BusRoomService {
    /**
     * 查询房号
     * @param buildingId
     * @param util
     * @return
     */
    ApiResult queryList(String buildingId,String util);

    List<BusRoom> list(QueryWrapper<BusRoom> queryWrapper);

    void save(BusRoom room,String lay);

    BusRoom getByBuildId(String buildId);

    BusRoom get(String id);

    /**
     * 根据楼栋删除房号
     */
    void deleteByBid(String buildingId);

    void updateByBuilding(BusBuilding busBuilding);
}
