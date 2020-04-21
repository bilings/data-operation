package com.hifo.dataoperation.mapper.base;

/**
 * 表名枚举类
 *
 * @author whc
 * @date 2019/3/15 9:31
 */
public enum Table {

    /**
     * 操作日志
     */
    hf_operation_log,

    /**
     * 序列号
     */
    hf_sequence,

    /**
     * 字典
     */
    hf_dictionary,

    /**
     * 用户
     */
    ext_employee,

    /**
     * 机构
     */
    ext_organization,

    /**
     * 机构设置
     */
    bus_setting,

    /**
     * 楼盘
     */
    bus_estate,

    /**
     * 楼栋
     */
    bus_building,

    /**
     * 房号
     */
    bus_room,

    /**
     * 合并记录
     */
    bus_merge_rel,

    /**
     * 虚拟记录
     */
    bus_virtual_rel,

    /**
     * 上传记录
     */
    bus_upload_log,

    /**
     * 模块数据，临时，以后会做到独立系统中
     */
    module,

    /**
     * 均价
     */
    bus_estate_avg_price,

    /**
     * 机构独立行政区划
     */
    bus_administrative,

    /**
     * 客户机构
     */
    ext_customer_org,

    /**
     * 客户信息
     */
    ext_customer_user,

    /**
     * 系数分组
     */
    bus_coe_group,

    /**
     * 系数结构
     */
    bus_coe_structure,

    /**
     * 系数项
     */
    bus_coe_item,

    /**
     * 楼层系数
     */
    bus_common_coe_floor,

    /**
     * 朝向系数
     */
    bus_common_coe_orientation,

    /**
     * 面积系数
     */
    bus_common_coe_area,

    /**
     * 建筑结构系数
     */
    bus_common_coe_structure,

    /**
     * 景观系数
     */
    bus_common_coe_scenery,

    /**
     * 合并推荐
     */
    bus_estate_agent,

    /**
     * 机构城市
     */
    bus_organization_city
}
