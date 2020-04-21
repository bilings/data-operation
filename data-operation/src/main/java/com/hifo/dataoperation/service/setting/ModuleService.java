package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.Module;
import com.hifo.dataoperation.vo.TreeVO;

/**
 * 模块接口
 *
 * @Author: xmw
 * @Date: 2019/5/23 13:46
 */
public interface ModuleService extends IService<Module> {

    /**
     * 获取模块树
     *
     * @return
     */
    TreeVO moduleTree();
}
