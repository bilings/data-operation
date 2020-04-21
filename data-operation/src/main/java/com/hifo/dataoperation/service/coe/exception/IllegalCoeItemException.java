package com.hifo.dataoperation.service.coe.exception;
/**
 * 
 * @author 杨捷
 * @date 2019年5月7日
 * @description 非法的系数项
 */
public class IllegalCoeItemException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6144994913937516548L;

	public IllegalCoeItemException(String message) {
		super(message);
	}
}
