package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.mapper.setting.ExtOrganizationMapper;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.entity.ExtOrganization;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.ExtOrganizationService;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.util.EntityUtils;
import com.hifo.dataoperation.vo.ExtOrganizationVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 评估机构接口
 *
 * @author jinzhichen
 * @date 2019/4/12 15:22
 */
@Service
public class ExtOrganizationServiceImpl extends MybatisPlusService<ExtOrganizationMapper, ExtOrganization> implements ExtOrganizationService {

    @Autowired
    private BasicService basicService;

    /**
     * 新增评估机构
     *
     * @return boolean
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult extOrganizationAdd(ExtOrganizationVO vo) {
        ExtOrganization extOrganization = new ExtOrganization();
        EntityUtils.copyProperties(vo, extOrganization);
        if (this.saveOrUpdate(extOrganization)) {
            return ApiResult.success(vo);
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 删除评估机构
     *
     * @return boolean
     */
    @Override
    public ApiResult extOrganizationDel(Long id) {
        if (this.removeById(id)) {
            return ApiResult.success(id);
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 编辑评估机构
     *
     * @return boolean
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult extOrganizationUpdate(Long id, ExtOrganizationVO vo) {
        ExtOrganization extOrganization = new ExtOrganization();
        EntityUtils.copyProperties(vo, extOrganization);
        extOrganization.setId(id);
        if (this.updateById(extOrganization)) {
            return ApiResult.success(vo);
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 批量编辑评估机构
     *
     * @return List<ExtOrganizationVO>
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult extOrganizationBatchUpdate(List<ExtOrganization> list) {
        if (this.updateBatchById(list)) {
            return ApiResult.success(list);
        } else {
            return ApiResult.failed();
        }
    }

    /**
     * 分页查询评估机构(自定义sql)
     *
     * @return ApiResult
     */
    @Override
    public ApiResult extOrganizationList(Pagination page, ExtOrganizationVO vo) {
        page.setRecords(baseMapper.extOrganizationList(page, vo));
        return ApiResult.success(page);
    }

    /**
     * 分页查询评估机构(wrapper方式)
     *
     * @return ApiResult
     */
    @Override
    public ApiResult extOrganizationListWrapper(Pagination page, ExtOrganizationVO vo) {
        QueryWrapper wrapper = new QueryWrapper<ExtOrganization>();
        //根据名称查询
        if (StringUtils.isNotBlank(vo.getName())) {
            wrapper.like("name", vo.getName());
        }
        //根据使用次数
        if (null != vo.getTimes()) {
            wrapper.eq("times", vo.getTimes());
        }
        baseMapper.selectPage(page, wrapper);
        return ApiResult.success(page);
    }

    /**
     * 查询机构信息
     *
     * @return ApiResult
     */
    @Override
    public ApiResult qryExtOrganization() {
        ExtEmployee extEmployee = basicService.getLoginEmployee();
        String id = extEmployee.getOrganizationId() + "";
        return ApiResult.success(this.getById(id));
    }

    /**
     * 更新机构信息
     *
     * @param vo ExtOrganization
     * @return ApiResult
     */
    @Override
    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    public ApiResult update(ExtOrganizationVO vo) {
        ExtEmployee extEmployee = basicService.getLoginEmployee();
        Integer id = extEmployee.getOrganizationId();
        ExtOrganization extOrganization = new ExtOrganization();
        EntityUtils.copyProperties(vo, extOrganization);
        extOrganization.setId(Long.valueOf(id));
        if (this.updateById(extOrganization)) {
            return ApiResult.success(vo);
        } else {
            return ApiResult.failed();
        }
    }


}
