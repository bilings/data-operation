package com.hifo.dataoperation.service.setting;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.controller.ApiResult;
import com.hifo.dataoperation.entity.ExtOrganization;
import com.hifo.dataoperation.pagination.Pagination;
import com.hifo.dataoperation.vo.ExtOrganizationVO;

import java.util.List;


/**
 * 评估机构接口
 *
 * @author jinzhichen
 * @date 2019/4/12 15:22
 */
public interface ExtOrganizationService extends IService<ExtOrganization> {

    /**
     * 新增评估机构
     *
     * @param vo
     * @return
     */
    ApiResult extOrganizationAdd(ExtOrganizationVO vo);

    /**
     * 删除评估机构
     *
     * @param id
     * @return
     */
    ApiResult extOrganizationDel(Long id);

    /**
     * 编辑评估机构
     *
     * @param id
     * @param vo
     * @return
     */
    ApiResult extOrganizationUpdate(Long id, ExtOrganizationVO vo);

    /**
     * 批量编辑评估机构
     *
     * @param list
     * @return
     */
    ApiResult extOrganizationBatchUpdate(List<ExtOrganization> list);

    /**
     * 分页查询评估机构(自定义sql)
     *
     * @param page
     * @param vo
     * @return
     */
    ApiResult extOrganizationList(Pagination page, ExtOrganizationVO vo);

    /**
     * 分页查询评估机构(wrapper方式)
     *
     * @param page
     * @param vo
     * @return
     */
    ApiResult extOrganizationListWrapper(Pagination page, ExtOrganizationVO vo);

    /**
     * 查询机构信息
     *
     * @return
     */
    ApiResult qryExtOrganization();

    /**
     * 更新机构信息
     *
     * @param vo
     * @return
     */
    ApiResult update(ExtOrganizationVO vo);


}
