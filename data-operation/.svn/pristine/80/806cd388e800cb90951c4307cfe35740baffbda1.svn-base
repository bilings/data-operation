package com.hifo.dataoperation.mapper.estate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.BusEstate;
import com.hifo.dataoperation.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 楼盘操作接口
 *
 * @author whc
 * @date 2019/3/13 15:32
 */
public interface BusEstateMapper extends BaseMapper<BusEstate> {

    /**
     * 根据楼盘条件查询楼盘
     *
     * @param page
     * @param busEstate
     * @return
     */
    List<BusEstate> selectPageByEstate(Pagination page, @Param("busEstate") BusEstate busEstate);

    /**
     * 根据条件，查询分页楼盘数据
     * @param busEstate 条件
     * @param start 起始点
     * @param limit 数量
     * @return 结果
     */
    List<BusEstate> query(@Param("busEstate") BusEstate busEstate, @Param("start") int start, @Param("limit") int limit);
    /**
     * 根据条件，查询分页楼盘数据
     * @param name 条件
     * @param calDate 条件
     * @param start 起始点
     * @param limit 数量
     * @return 结果
     */
    List<BusEstate> queryList(@Param("name") String name, @Param("calDate") String calDate, @Param("start") int start, @Param("limit") int limit);
}
