package com.hifo.dataoperation.service.coe.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.base.MybatisPlusService;
import com.hifo.dataoperation.entity.coe.BaseBusCommonCoe;
import com.hifo.dataoperation.entity.coe.BusCoeGroup;
import com.hifo.dataoperation.entity.coe.BusCoeStructure;
import com.hifo.dataoperation.mapper.coe.BusCoeGroupMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.service.coe.CoeService;
import com.hifo.dataoperation.util.EntityUtils;
import com.hifo.dataoperation.vo.BaseCoe;

/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description 根据业务规则，系统必须自行初始化顶级（市级）系数，否则初始化时无法得到默认系数
 */
public abstract class BaseCoeServiceImpl<M extends BaseMapper<E>, E extends BaseBusCommonCoe, T extends BaseCoe<D>, D>
		extends MybatisPlusService<M, E> implements CoeService<T, D> {

	@Autowired
	private BusCoeGroupMapper busCoeGroupMapper;
	@Autowired
	private BasicService basicService;

	/**
	 * 将dto转化为entity
	 * 
	 * @param coe
	 * @return
	 */
	private List<E> convert2Entity(T coe) {
		if (coe != null) {
			ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
			//获取第二个泛型
			Class<E> eClass = (Class<E>) parameterizedType.getActualTypeArguments()[1];
			E entity = null;

			Long structureId = coe.getStructureId();
			String desc = coe.getDescription();
			List<D> dtoList = coe.getDetailList();
			List<E> res = new ArrayList<E>();
			if (dtoList != null) {

				for (D detail : dtoList) {
					try {
						entity = eClass.newInstance();
					} catch (InstantiationException | IllegalAccessException exception) {
						exception.printStackTrace();
					}
					EntityUtils.copyProperties(detail, entity);
					entity.setDescription(desc);
					entity.setCoeStructureId(structureId);
					entity.setCreateTime(new Date(System.currentTimeMillis()));
					entity.setCoe(entity.getCoe());
					entity.setOrganizationId(basicService.getLoginEmployee().getOrganizationId().longValue());
					res.add(entity);
				}

			}
			return childToEntity(coe, res);
		}
		return null;
	}

	/**
	 * 将一个dto明细转化为entity的过程中，子类需要处理的独有逻辑，放在此方法中
	 * @param coe
	 * @param entityList
	 * @return
	 */
	protected abstract List<E> childToEntity(T coe, List<E> entityList);

	/**
	 * 将一个entity转化为dto明细的过程中，子类需要处理的独有逻辑，放在此方法中
	 * 
	 * @param detailList
	 * @param structureId
	 * @param dto         父类中已经预处理过
	 * @return
	 */
	protected abstract T childToDto(List<E> detailList, Long structureId, T dto);

	/**
	 * 将数据库的数据明细列表转化为dto
	 * 
	 * @param detailList
	 * @param structureId
	 * @return
	 */
	private T convert2Dto(List<E> detailList, Long structureId) {
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获取第四个泛型
		Class<D> dClass = (Class<D>) parameterizedType.getActualTypeArguments()[3];
		//获取第三个泛型
		Class<T> tClass = (Class<T>) parameterizedType.getActualTypeArguments()[2];
		T res = null;
		try {
			res = tClass.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		List<D> dtoList = new ArrayList<D>();
		String desc = null;
		if (detailList != null && !detailList.isEmpty()) {
			try {
				for (E entity : detailList) {
					D d = dClass.newInstance();
					EntityUtils.copyProperties(entity, d);
					dtoList.add(d);
					desc = entity.getDescription();
				}
				
				res.setStructureId(structureId);
				res.setDescription(desc);
				res.setDetailList(dtoList);
				res = childToDto(detailList, structureId, res);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return res;
	}

	/**
	 * 获取分组下根结构对应的系数列表
	 * 
	 * @param groupId
	 * @return
	 */
	protected abstract List<E> getRootCoe(long groupId);

	@Override
	@Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
	public boolean initAndSaveCoe(BusCoeStructure structure) {
		T coe = initOrientationCoe(structure);
		return saveCoe(coe);
	}

	@Override
	@Transactional(value = "hifoTransactionManager", rollbackFor = Exception.class)
	public boolean saveCoe(T coe) {
		Long structureId = coe.getStructureId();
		QueryWrapper<E> delWrapper = new QueryWrapper<E>();
		delWrapper.eq("coeStructureId", structureId);
		this.remove(delWrapper);
		List<E> list = convert2Entity(coe);
		return this.saveBatch(list);
	}

	@Override
	public T getCoeByStructureId(Long structureId) {
		QueryWrapper<E> queryWrapper = new QueryWrapper<E>();
		queryWrapper.eq("coeStructureId", structureId).eq("organizationId",
				basicService.getLoginEmployee().getOrganizationId().longValue());
		return convert2Dto(this.baseMapper.selectList(queryWrapper), structureId);
	}

	private T initOrientationCoe(BusCoeStructure structure) {

		// pid为空，此系数是在创建分组时自动生成的
		if (structure.getPid() == null) {
			BusCoeGroup group = busCoeGroupMapper.selectById(structure.getGroupId());
			return initCoeNoPid(group, structure);
		}
		// pid不为空，此系数是在创建结构时产生的
		else {
			return initCoeWithPid(structure);
		}

	}

	/**
	 * 对应结构有父级结构，自动继承父级结构的系数
	 * 
	 * @param structure
	 * @return
	 */
	private T initCoeWithPid(BusCoeStructure structure) {
		T res = getCoeByStructureId(structure.getPid());
		res.setStructureId(structure.getId());
		return res;
	}

	private T initCoeNoPid(BusCoeGroup group, BusCoeStructure structure) {
		long parentGroupId = group.getpId();
		List<E> detailList = getRootCoe(parentGroupId);

		// 父级分组有系数
		if (detailList != null && !detailList.isEmpty()) {
			return convert2Dto(detailList, structure.getId());
		}
		// 父级分组无系数,再找上一级（应该是市级）的系数
		else {
			BusCoeGroup parentGroup = busCoeGroupMapper.selectById(parentGroupId);
			if (parentGroup != null) {
				// 市一级分组对应的系数
				List<E> greatDetailList = getRootCoe(parentGroup.getpId());
				if (greatDetailList != null && !greatDetailList.isEmpty()) {
					return convert2Dto(greatDetailList, structure.getId());
				}
			}
		}
		return null;
	}

}
