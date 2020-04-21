package com.hifo.dataoperation.mapper.setting;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.hifo.dataoperation.entity.BusAdministrative;
import com.hifo.dataoperation.pagination.Pagination;


/**
 * 行政区划数据操作接口
 *
 * @author 杨捷
 * @Date 2019年4月24日
 */
public interface BusAdministrativeMapper extends BaseMapper<BusAdministrative> {

    /**
     * 根据条件，分页查询行政区划数据
     *
     * @param busAdministrative 条件
     * @return 结果
     */
    List<BusAdministrative> query(Pagination pages, @Param("busAdministrative") BusAdministrative busAdministrative);


}
