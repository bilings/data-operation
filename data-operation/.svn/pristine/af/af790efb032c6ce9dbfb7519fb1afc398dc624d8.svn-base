package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.Module;
import com.hifo.dataoperation.mapper.setting.ModuleMapper;
import com.hifo.dataoperation.service.setting.ModuleService;
import com.hifo.dataoperation.util.TreeUtils;
import com.hifo.dataoperation.vo.TreeVO;
import org.springframework.stereotype.Service;

/**
 * 模块业务实现类
 *
 * @Author: xmw
 * @Date: 2019/5/23 13:47
 */
@Service
public class ModuleServiceImpl extends MybatisPlusService<ModuleMapper, Module> implements ModuleService {

    @Override
    public TreeVO moduleTree() {
        return TreeUtils.getTreeByList(baseMapper.selectList(new QueryWrapper<Module>().orderByAsc("id")),true);
    }

}
