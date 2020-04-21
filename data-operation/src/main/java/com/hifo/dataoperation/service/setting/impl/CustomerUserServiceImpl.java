package com.hifo.dataoperation.service.setting.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.CustomerOrg;
import com.hifo.dataoperation.entity.CustomerUser;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.setting.CustomerUserMapper;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.setting.CustomerOrgService;
import com.hifo.dataoperation.service.setting.CustomerUserService;
import com.hifo.dataoperation.util.Md5Util;
import com.hifo.dataoperation.util.StringUtil;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 客户信息业务类
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:44
 */
@Service
public class CustomerUserServiceImpl extends MybatisPlusService<CustomerUserMapper, CustomerUser> implements CustomerUserService {

    @Autowired
    private BasicService basicService;
    @Autowired
    private CustomerOrgService customerOrgService;


    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult update(CustomerUser user) {
        var organizationId = basicService.getLoginEmployee().getOrganizationId();
        if (user.getId() == null) {
            var count = baseMapper.selectCount(new QueryWrapper<CustomerUser>().eq("organizationId", organizationId).eq("username", user.getUsername()).eq("isDelete", 0));
            if (count > 0) {
                return ApiResult.failed("新建失败：客户已存在。");
            }
            user.setIsDelete(false);
            // 现在账号为手机号
            user.setTel(Long.parseLong(user.getUsername()));
            // 默认密码手机号后6位
            user.setPassword(Md5Util.md5(user.getUsername().substring(5)));
        } else {
            var count = baseMapper.selectCount(new QueryWrapper<CustomerUser>().eq("organizationId", organizationId).eq("username", user.getUsername()).eq("isDelete", 0).ne("id", user.getId()));
            if (count > 0) {
                return ApiResult.failed("更新失败：客户已存在。");
            }
            //TODO 现在账号为手机号
            user.setTel(Long.parseLong(user.getUsername()));
        }
        // 选取机构时从机构中获取机构信息
        if (user.getOrgId() != null) {
            // 设置机构信息
            var org = customerOrgService.getById(user.getOrgId());
            user.setOrgName(org.getName());
            user.setType(org.getType());
            user.setRank(org.getRank());
        }
        return ApiResult.success(saveOrUpdate(user));
    }

    @Override
    public CustomerUser getDetailById(Long id) {
        var user = baseMapper.selectById(id);
        if (user != null && user.getOrgId() != null) {
            var org = customerOrgService.getDetailById(user.getOrgId());
            // 最上級机构
            if (org != null) {
                user.setTimes(org.getTimes());
            }
        }
        return user;
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult delete(String[] ids) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var obj = baseMapper.selectById(ids[i]);
            if (obj != null) {
                obj.setIsDelete(true);
                updateById(obj);
            }
        }
        return ApiResult.success("删除成功，共删除" + length + "条信息。");
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult resetPassword(String[] ids) {
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var obj = getById(ids[i]);
            if (obj != null) {
                obj.setPassword(Md5Util.md5(obj.getUsername().substring(5)));
                updateById(obj);
            }
        }
        return ApiResult.success("重置成功，共重置" + length + "条信息。");
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult resetPassword(String id, String password, String pwd) {
        var obj = getById(id);
        if (obj == null) {
            return ApiResult.failed("当前用户不存在。");
        }
        if (StringUtil.isEmpty(pwd) || !obj.getPassword().equals(Md5Util.md5(pwd))) {
            return ApiResult.failed("原密码不正确。");
        }
        if (StringUtil.isEmpty(password)) {
            return ApiResult.failed("输入的密码不符合规范。");
        }
        if (obj.getPassword().equals(Md5Util.md5(password.trim()))) {
            return ApiResult.failed("新密码与原始密码一致。");
        }
        obj.setPassword(Md5Util.md5(password.trim()));
        updateById(obj);
        return ApiResult.success();
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult updateByOrg(CustomerOrg org) {
        baseMapper.updateByOrgId(org.getName(), org.getType(), org.getRank(), org.getId().intValue());
        return ApiResult.success();
    }

    /**
     * 格式化列表查询条件
     *
     * @param user
     * @param employee
     * @param page
     * @return
     */
    public static QueryWrapper findListQuery(CustomerUser user, ExtEmployee employee, Pagination page) {
        var query = new QueryWrapper<CustomerUser>();
        // 设置机构
        query.eq("organizationId", employee.getOrganizationId());
        query.eq("isDelete", 0);
        if (StringUtil.isNotEmpty(user.getUsername())) {
            query.like("username", user.getUsername());
            query.or();
            query.like("name", user.getUsername());
            query.or();
            query.like("orgName", user.getUsername());
            query.or();
            query.like("type", user.getUsername());
        }
        if (StringUtil.isNotEmpty(user.getName())) {
            query.like("name", user.getName());
        }
        if (StringUtil.isNotEmpty(user.getType())) {
            query.eq("type", user.getType());
        }
        if (user.getOrgId() != null) {
            query.eq("orgId", user.getOrgId());
        }
        if (StringUtil.isNotEmpty(user.getRank())) {
            query.apply("FIND_IN_SET({0}, rank)>0", user.getRank());
        }
        if (page != null) {
            if (StringUtil.isEmpty(page.ascs()) && StringUtil.isEmpty(page.descs())) {
                query.orderByDesc("updateTime");
            }
        }
        return query;
    }

    @Override
    public List<CustomerUser> list(CustomerUser user) {
        var employee = basicService.getLoginEmployee();
        var orgMap = new HashMap<String, CustomerOrg>(10);
        var list = (List<CustomerUser>) baseMapper.selectList(findListQuery(user, employee, null));
        list.stream().forEach(e -> {
            // 客户所属机构 则剩余查询次数为所在机构次数
            if (e.getOrgId() != null) {
                var parentsId = e.getRank().split(",")[0];
                if (orgMap.get(parentsId) == null) {
                    var org = customerOrgService.getById(parentsId);
                    orgMap.put(parentsId, org);
                    e.setTimes(org.getTimes());
                } else {
                    e.setTimes(orgMap.get(parentsId).getTimes());
                }
            }
        });
        return list;
    }

    @Override
    public CustomerUser listByOpenid(CustomerUser vo) {
        QueryWrapper wrapper = new QueryWrapper<CustomerUser>();
        if (StringUtils.isNotBlank(vo.getOpenid())) {
            wrapper.eq("openid", vo.getOpenid());
        }
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public IPage<CustomerUser> findByPage(Pagination page, CustomerUser user) {
        var employee = basicService.getLoginEmployee();
        var orgMap = new HashMap<String, CustomerOrg>(10);
        var pages = baseMapper.selectPage(page, findListQuery(user, employee, page));
        ((List<CustomerUser>) pages.getRecords()).stream().forEach(e -> {
            // 客户所属机构 则剩余查询次数为所属机构剩余次数
            if (e.getOrgId() != null) {
                var parentsId = e.getRank().split(",")[0];
                if (orgMap.get(parentsId) == null) {
                    var org = customerOrgService.getById(parentsId);
                    orgMap.put(parentsId, org);
                    e.setTimes(org.getTimes());
                } else {
                    e.setTimes(orgMap.get(parentsId).getTimes());
                }
            }
        });
        return pages;
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult updateByOrgDelete(int orgId) {
        var employee = basicService.getLoginEmployee();
        var list = baseMapper.selectList(new QueryWrapper<CustomerUser>().eq("organizationId", employee.getOrganizationId()).eq("orgId", orgId).eq("isDelete", false));
        list.stream().forEach(e -> {
            e.setOrgId(null);
            e.setOrgName(null);
            e.setRank(null);
            e.setTimes(0);
            updateById(e);
        });
        return ApiResult.success(list.size());
    }

    @Override
    public int updateOpenidById(CustomerUser customerUser) {
        return baseMapper.updateById(customerUser);
    }

    @Override
    public int updateUnUserInfoById(CustomerUser obj) {
        return baseMapper.updateById(obj);
    }

    @Override
    public ApiResult useSelectUpdateTimesUnUserInfo(Long id) {
        var user = baseMapper.selectById(id);
        if (user != null) {
            if (user.getOrgId() != null) {
                // 最上级机构次数减一
                var org = customerOrgService.getById(user.getRank().split(",")[0]);
                if (org != null) {
                    if (org.getTimes().intValue() == 0) {
                        return ApiResult.failed("用户查询次数已用尽，不可查询。");
                    }
                    org.setTimes(org.getTimes() - 1);
                    customerOrgService.updateUnUserInfoById(org);
                }
            } else {
                if (user.getTimes().intValue() == 0) {
                    return ApiResult.failed("用户查询次数已用尽，不可查询。");
                }
                user.setTimes(user.getTimes() - 1);
                baseMapper.updateById(user);
            }
            return ApiResult.success();
        }
        return ApiResult.failed("用户不存在。");
    }

    @Override
    public CustomerUser wxSelectOne(CustomerUser customerUser) {
        QueryWrapper wrapper = new QueryWrapper<CustomerUser>();
        if (StringUtils.isNotBlank(customerUser.getUsername())) {
            wrapper.eq("username", customerUser.getUsername());
        }
        if (customerUser.getOrganizationId() > 0) {
            wrapper.eq("organizationId", customerUser.getOrganizationId());
        }
        return baseMapper.selectOne(wrapper);
    }

    @Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
    @Override
    public ApiResult useSelectUpdateTimes(Long id) {
        var user = baseMapper.selectById(id);
        if (user != null) {
            if (user.getOrgId() != null) {
                // 最上级机构次数减一
                var org = customerOrgService.getById(user.getRank().split(",")[0]);
                if (org != null) {
                    if (org.getTimes().intValue() == 0) {
                        return ApiResult.failed("用户查询次数已用尽，不可查询。");
                    }
                    org.setTimes(org.getTimes() - 1);
                    customerOrgService.update(org);
                }
            } else {
                if (user.getTimes().intValue() == 0) {
                    return ApiResult.failed("用户查询次数已用尽，不可查询。");
                }
                user.setTimes(user.getTimes() - 1);
                baseMapper.updateById(user);
            }
            return ApiResult.success();
        }
        return ApiResult.failed("用户不存在。");
    }

}
