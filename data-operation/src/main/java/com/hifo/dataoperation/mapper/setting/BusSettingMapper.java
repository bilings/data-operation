package com.hifo.dataoperation.mapper.setting;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.BusSetting;
import com.hifo.dataoperation.entity.ExtOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构设置
 *
 * @author jinzhichen
 * @date 2019/4/12 15:28
 */
public interface BusSettingMapper extends BaseMapper<BusSetting> {

    /**
     * 分页查询机构设置
     *
     * @param vo
     * @return
     */
    List<ExtOrganization> busSettingList(@Param("busSetting") BusSetting vo);
}
