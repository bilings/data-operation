package com.hifo.dataoperation.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.CustomerUser;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: xmw
 * @Date: 2019/5/5 16:38
 */
public interface CustomerUserMapper extends BaseMapper<CustomerUser> {

    /**
     * 根据orgId修改客户信息
     *
     * @param orgName
     * @param type
     * @param rank
     * @param orgId
     */
    void updateByOrgId(@Param("orgName") String orgName, @Param("type") Integer type, @Param("rank") String rank, @Param("orgId") Integer orgId);



}
