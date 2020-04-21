package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.CustomerOrg;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.vo.TreeVO;

import java.util.List;

/**
 * 客户机构业务接口
 *
 * @Author: xmw
 * @Date: 2019/5/5 16:41
 */
public interface CustomerOrgService extends IService<CustomerOrg> {

    /**
     * 新增或更新客户机构
     *
     * @param org
     * @return
     */
    ApiResult update(CustomerOrg org);

    /**
     * 根据id获取机构详情
     *
     * @param id
     * @return
     */
    CustomerOrg getDetailById(Long id);

    /**
     * 批量删除客户机构
     *
     * @param ids
     * @return
     */
    ApiResult delete(String[] ids);

    /**
     * 查询客户机构列表
     *
     * @param org
     * @return
     */
    List<CustomerOrg> list(CustomerOrg org);

    /**
     * 分页查询客户机构列表
     *
     * @param page
     * @param org
     * @return
     */
    IPage<CustomerOrg> findByPage(Pagination page, CustomerOrg org);

    /**
     * 查询客户机构树
     *
     * @param org
     * @return
     */
    TreeVO findByTree(CustomerOrg org);

    /**
     * 修改机构信息不设置修改人
     *
     * @param org
     * @return
     */
    int updateUnUserInfoById(CustomerOrg org);
}
