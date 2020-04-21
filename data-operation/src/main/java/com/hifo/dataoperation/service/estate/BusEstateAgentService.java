package com.hifo.dataoperation.service.estate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.vo.BusEstateAgentVO;

/**
 * 合并推荐业务接口
 *
 * @Author: xmw
 * @Date: 2019/5/24 15:10
 */
public interface BusEstateAgentService extends IService<BusEstateAgentVO> {

    /**
     * 楼盘推荐分页列表
     *
     * @param vo
     * @param pages
     * @return
     */
    IPage findByPage(BusEstateAgentVO vo, Pagination pages);

    /**
     * 批量合并楼盘
     *
     * @param ids
     * @return
     */
    ApiResult merges(String[] ids);

    /**
     * 异步更新
     *
     * @param agent
     * @param emp
     * @return
     */
    Boolean updateByIdAsync(BusEstateAgentVO agent, ExtEmployee emp);

    /**
     * 设置是否推荐
     *
     * @param ids
     * @param isRecommend
     * @return
     */
    ApiResult recommend(String[] ids, Integer isRecommend);
}
