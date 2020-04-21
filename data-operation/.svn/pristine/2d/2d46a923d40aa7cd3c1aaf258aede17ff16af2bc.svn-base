package com.hifo.dataoperation.mapper.estate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.vo.BusEstateAgentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合并推荐mapper
 *
 * @Author: xmw
 * @Date: 2019/5/24 15:06
 */
public interface BusEstateAgentMapper extends BaseMapper<BusEstateAgentVO> {

    /**
     * 楼盘合并推荐列表
     *
     * @param page
     * @param vo
     * @return
     */
    List<BusEstateAgentVO> selectAgentList(Pagination page, @Param("vo") BusEstateAgentVO vo);
}
