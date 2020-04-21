package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.CustomerOrg;
import com.hifo.dataoperation.entity.CustomerUser;
import com.hifo.dataoperation.pagination.Pagination;

import java.util.List;

/**
 * 客户信息业务接口
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:41
 */
public interface CustomerUserService extends IService<CustomerUser> {

    /**
     * 新增或更新客户信息
     *
     * @param user
     * @return
     */
    ApiResult update(CustomerUser user);


    /**
     * 用户信息详情
     *
     * @param id
     * @return
     */
    CustomerUser getDetailById(Long id);

    /**
     * 批量删除客户信息
     *
     * @param ids
     * @return
     */
    ApiResult delete(String[] ids);

    /**
     * 批量重置客户密码
     *
     * @param ids
     * @return
     */
    ApiResult resetPassword(String[] ids);

    /**
     * 根据机构修改客户信息
     *
     * @param org
     * @return
     */
    ApiResult updateByOrg(CustomerOrg org);

    /**
     * 查询客户信息列表
     *
     * @param user
     * @return
     */
    List<CustomerUser> list(CustomerUser user);

    /**
     * 微信登录根据oppenid查询用户信息
     *
     * @param user
     * @return
     */
    CustomerUser listByOpenid(CustomerUser user);

    /**
     * 分页查询客户信息列表
     *
     * @param page
     * @param user
     * @return
     */
    IPage<CustomerUser> findByPage(Pagination page, CustomerUser user);

    /**
     * 刪除机构的用户设为无机构
     *
     * @param orgId
     * @return
     */
    ApiResult updateByOrgDelete(int orgId);

    /**
     * 用户使用后减少相应可查询次数
     *
     * @param id
     * @return
     */
    ApiResult useSelectUpdateTimes(Long id);

    /**
     * 微信根据用户名和机构id查询用户信息
     *
     * @param customerUser
     * @return
     */
    CustomerUser wxSelectOne(CustomerUser customerUser);

    /**
     * 修改用户密码
     *
     * @param id
     * @param password
     * @param pwd
     * @return
     */
    ApiResult resetPassword(String id, String password, String pwd);

    /**
     * 微信端将openid更新到数据库
     * @param customerUser
     */
    int updateOpenidById(CustomerUser customerUser);

    /**
     * 更新信息不修改修改人
     *
     * @param obj
     * @return
     */
    int updateUnUserInfoById(CustomerUser obj);

    /**
     * 用户使用后减少相应可查询次数不修改修改人
     *
     * @param id
     * @return
     */
    ApiResult useSelectUpdateTimesUnUserInfo(Long id);
}
