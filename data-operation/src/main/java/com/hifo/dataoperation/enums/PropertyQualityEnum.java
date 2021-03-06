package com.hifo.dataoperation.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物业品质枚举
 * @author weisibin
 * @date 2020年4月8日16:17:01
 */
public enum PropertyQualityEnum {
	//"普通住宅（公寓）_10",      
	PT_ZZGY("10","普通住宅（公寓）"),
	//"高档公寓_11",
	GD_GY("11","高档公寓"),
	//"花园洋房_12",
	HY_YF("12","花园洋房"),
	//"联排别墅_13",
	LP_BS("13","联排别墅"),
	 //"叠拼别墅_14",
	DP_BS("14","叠拼别墅"),
	 //"双拼别墅_15",
	SP_BS("15","双拼别墅"),
	//"独立别墅_16",
	DL_BS("16","独立别墅"),
	//"类独栋_17",
	L_DD("17","类独栋"),
	//"别墅_18"
	BS("18","别墅");
	private String type;
	private String name;
	PropertyQualityEnum(String type,String name){
		this.type = type;
		this.name = name;
	}
	/**
	 * 获取类型
	 * @return String 类型
	 */
	public String getType() {
		return type;
	}
	/**
	 * 获取类型名
	 * @return String 类型名
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 根据状态码获取消息体
	 * @param type 类型
	 * @return String 类型名 
	 */
	public static String getName(String type) {
		for (PropertyQualityEnum ps : PropertyQualityEnum.values()) {
			if (ps.getType().equals(type)) {
				return ps.getName();
			}
		}
		return null;
	}
	
	/**
	 * 获取枚举数组
	 * @return
	 */
	public static List<Map<String,String>> toArray(){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,String> m = null;
		for (PropertyQualityEnum ps : PropertyQualityEnum.values()) {
			m = new HashMap<>();
			m.put("id", ps.getType());
			m.put("name", ps.getName());
			list.add(m);
		}
		return list;
	}
}
