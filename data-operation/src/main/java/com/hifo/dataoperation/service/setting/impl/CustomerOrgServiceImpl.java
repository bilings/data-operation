package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.CustomerOrg;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.setting.CustomerOrgMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.CustomerOrgService;
import com.hifo.dataoperation.service.setting.CustomerUserService;
import com.hifo.dataoperation.util.StringUtil;
import com.hifo.dataoperation.util.TreeUtils;
import com.hifo.dataoperation.vo.TreeVO;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 客户机构业务类
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:43
 */
@Service
public class CustomerOrgServiceImpl extends MybatisPlusService<CustomerOrgMapper, CustomerOrg> implements CustomerOrgService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private CustomerUserService customerUserService;

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult update(CustomerOrg org) {
        var organizationId = basicService.getLoginEmployee().getOrganizationId();
        // 默认可查询次数为0
        if (org.getTimes() == null) {
            org.setTimes(0);
        }
        if (org.getId() == null) {
            var count = baseMapper.selectCount(new QueryWrapper<CustomerOrg>().eq("organizationId", organizationId).eq("name", org.getName()).eq("type", org.getType()).eq("isDelete", 0));
            if (count > 0) {
                return ApiResult.failed("新建失败：机构已存在。");
            }
            org.setIsDelete(false);
            save(org);
            // 父机构为空
            if (org.getParentId() == null) {
                org.setRank(org.getId().toString());
            } else {
                org.setRank(org.getRank() + "," + org.getId());
            }
        } else {
            var count = baseMapper.selectCount(new QueryWrapper<CustomerOrg>().eq("organizationId", organizationId).eq("name", org.getName()).eq("type", org.getType()).eq("isDelete", 0).ne("id", org.getId()));
            if (count > 0) {
                return ApiResult.failed("更新失败：机构已存在。");
            }
            // 修改机构直属客户机构信息
            customerUserService.updateByOrg(org);
        }
        return ApiResult.success(saveOrUpdate(org));
    }

    @Override
    public CustomerOrg getDetailById(Long id) {
        var obj = baseMapper.selectById(id);
        var k = ",";
        // 为子机构时剩余查询次数为最上级机构查询次数
        if (obj != null && obj.getRank().indexOf(k) != -1) {
            obj.setTimes(baseMapper.selectById(obj.getRank().split(k)[0]).getTimes());
        }
        return obj;
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult delete(String[] ids) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            // 获取所有层级机构
            var list = baseMapper.selectList(new QueryWrapper<CustomerOrg>().apply("FIND_IN_SET({0}, rank)>0", ids[i]).eq("isDelete", false));
            list.stream().forEach(e -> {
                // 获取机构用户
                customerUserService.updateByOrgDelete(e.getId().intValue());
                // 删除层级机构
                e.setIsDelete(true);
                updateById(e);
            });
        }
        return ApiResult.success("删除成功。");
    }

    /**
     * 格式化列表查询条件
     *
     * @param org
     * @param employee
     * @param page
     * @return
     */
    public static QueryWrapper findListQuery(CustomerOrg org, ExtEmployee employee, Pagination page) {
        var query = new QueryWrapper<CustomerOrg>();
        // 设置机构
        query.eq("organizationId", employee.getOrganizationId());
        query.eq("isDelete", 0);
        if (StringUtil.isNotEmpty(org.getName())) {
            query.like("name", org.getName());
        }
        if (StringUtil.isNotEmpty(org.getType())) {
            query.eq("type", org.getType());
        }
        if (org.getParentId() != null) {
            query.eq("parentId", org.getParentId());
        }
        if (StringUtil.isNotEmpty(org.getRank())) {
            query.apply("FIND_IN_SET({0}, rank)>0", org.getRank());
        }
        if (page != null) {
            if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
                query.orderByDesc("updateTime");
            }
        }
        return query;
    }

    @Override
    public List<CustomerOrg> list(CustomerOrg org) {
        var employee = basicService.getLoginEmployee();
        return baseMapper.selectList(findListQuery(org, employee, null));
    }

    @Override
    public IPage<CustomerOrg> findByPage(Pagination page, CustomerOrg org) {
        var employee = basicService.getLoginEmployee();
        return baseMapper.selectPage(page, findListQuery(org, employee, page));
    }

    @Override
    public TreeVO findByTree(CustomerOrg org) {
        var employee = basicService.getLoginEmployee();
        var map = new LinkedHashMap();
        var list = baseMapper.selectList(findListQuery(org, employee, null));
        if (StringUtil.isNotEmpty(org.getName()) || StringUtil.isNotEmpty(org.getType())) {
            var size = list.size();
            for (var i = 0; i < size; i++) {
                org = (CustomerOrg) list.get(i);
                var ps = baseMapper.selectList(new QueryWrapper<CustomerOrg>().eq("organizationId", employee.getOrganizationId()).eq("isDelete", 0).apply("FIND_IN_SET(id,{0})>0", org.getRank()));
                for (var j = 0; j < ps.size(); j++) {
                    org = ps.get(j);
                    map.put(org.getId(), org);
                }
            }
            return TreeUtils.getTreeByMap(map);
        } else {
            return TreeUtils.getTreeByList(list);
        }

    }

    @Override
    public int updateUnUserInfoById(CustomerOrg org) {
        return baseMapper.updateById(org);
    }

}
