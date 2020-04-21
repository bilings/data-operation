package com.hifo.dataoperation.mapper.setting;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.entity.ExtOrganization;
import com.hifo.dataoperation.vo.ExtOrganizationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估机构
 *
 * @author jinzhichen
 * @date 2019/4/12 15:28
 */
public interface ExtOrganizationMapper extends BaseMapper<ExtOrganization> {

    /**
     * 分页查询评估机构
     *
     * @param page
     * @param vo
     * @return
     */
    List<ExtOrganization> extOrganizationList(IPage page, @Param("extOrganization") ExtOrganizationVO vo);
}
