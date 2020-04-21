package com.hifo.dataoperation.service.estate;

import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.dto.BusWaitMergeEtyDto;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.entity.mongo.BuiltEstate;
import com.hifo.dataoperation.entity.mongo.BusWaitMergeEty;
import com.hifo.dataoperation.pagination.Pagination;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface BuiltService {
    Pagination<BuiltEstate> findByPage(BuiltEstate busEstate, Pagination pages);

    ApiResult add(List<BusEstate> busEstate);

    ApiResult delete(List<BuiltEstate> busEstate);

    ApiResult merges(List<Map<String,String>> bl);
    
    /**
     * 分页查询待合并数据
     * @param bwd 查询参数
     * @param pages 分页参数
     * @return Pagination<BuiltEstate> 分页对象
     */
    public Pagination<BusWaitMergeEty> findWaitMergeByPage(BusWaitMergeEtyDto bwd, Pagination pages);
    
}
