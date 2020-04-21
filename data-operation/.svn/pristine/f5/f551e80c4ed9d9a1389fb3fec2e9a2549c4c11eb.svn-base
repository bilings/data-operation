package com.hifo.dataoperation.mapper.crm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.ExtEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户操作接口
 *
 * @author whc
 * @date 2019/3/13 15:32
 */
public interface ExtEmployeeMapper extends BaseMapper<ExtEmployee> {

    /**
     * 查询Employee
     *
     * @param organizationId 机构id
     * @param keyword        关键字（name/username）
     * @return List
     */
    List<ExtEmployee> query(@Param("organizationId") int organizationId, @Param("keyword") String keyword);
}
