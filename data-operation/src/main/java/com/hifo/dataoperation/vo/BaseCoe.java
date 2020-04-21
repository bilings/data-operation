package com.hifo.dataoperation.vo;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author 杨捷
 * @date 2019年5月5日
 * @description
 */
@Data
public class BaseCoe<D> {
	private Long id;
	private Long structureId;
	private String description;
	private List<D> detailList;
}
