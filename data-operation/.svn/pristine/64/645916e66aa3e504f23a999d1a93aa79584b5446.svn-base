package com.hifo.dataoperation.service.coe;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.entity.coe.BusOrganizationCoetype;

/**
 * <p>
 *  算法修正因素服务类
 * </p>
 *
 * @author 杨捷
 * @since 2019-05-24
 */
public interface BusOrganizationCoetypeService extends IService<BusOrganizationCoetype> {
	/**
	 * 获取当前用户所属机构使用的算法因素
	 * @param rganizationId
	 * @return
	 */
	public BusOrganizationCoetype getCoeTypes(String administrativeId);
	
	/**
	 * 设置当前用户所属机构使用的算法因素
	 * @param coeType
	 * @return
	 */
	public boolean setCoeType(BusOrganizationCoetype coeType);
}
