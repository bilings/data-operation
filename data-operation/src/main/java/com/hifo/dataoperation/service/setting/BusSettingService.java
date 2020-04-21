package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.BusSetting;
import com.hifo.dataoperation.vo.BusSettingVO;
import org.springframework.stereotype.Service;



/**
 * 机构设置
 *
 * @author jinzhichen
 * @date 2019/4/12 15:22
 */
@Service
public interface BusSettingService extends IService<BusSetting> {

    /**
     * 询价单-查询机构设置信息
     *
     * @param vo
     * @return
     */
    ApiResult query(BusSettingVO vo);

    /**
     * 根据appid查询机构id srcret
     *
     * @param vo
     * @return
     */
    BusSetting queryByAppid(BusSetting vo);

    /**
     * 询价单-保存或更新机构设置信息
     *
     * @param busSetting
     * @return
     */
    ApiResult updateBusSetting(BusSetting busSetting);

}
