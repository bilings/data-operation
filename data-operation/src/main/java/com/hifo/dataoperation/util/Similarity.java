package com.hifo.dataoperation.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/26.
 */
public class Similarity {

	// 楼盘名称/楼盘地址
	// isMerge字段，
	// 楼盘采集原始表：community --》清洗工具-》放入 warehouse_community_standard
	// 表——》合并（）到——》warehouse_community_service（数据利用-对外提供） 表（同城同行政区楼盘唯一）
	// 案例采集原始表：数据在案例采集库中（内网） --》清洗工具 -》放入 warehouse_caseinfo_standard 表——》
	// 求余弦相似度
	public static double sim(String str1, String str2) {

		Map<Character, int[]> vectorMap = new HashMap<>();

		int[] tempArray = null;

		for (Character character1 : str1.toCharArray()) {
			if (vectorMap.containsKey(character1)) {
				vectorMap.get(character1)[0]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 1;
				tempArray[1] = 0;
				vectorMap.put(character1, tempArray);
			}
		}
		for (Character character2 : str2.toCharArray()) {
			if (vectorMap.containsKey(character2)) {
				vectorMap.get(character2)[1]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 0;
				tempArray[1] = 1;
				vectorMap.put(character2, tempArray);
			}
		}
		double result = 0;
		result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
		return result;
	}

	private static double sqrtMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		result = squares(paramMap);
		result = Math.sqrt(result);
		return result;
	}

	// 求平方和
	private static double squares(Map<Character, int[]> paramMap) {
		double result1 = 0;
		double result2 = 0;
		Set<Character> keySet = paramMap.keySet();
		for (Character character : keySet) {
			int temp[] = paramMap.get(character);
			result1 += (temp[0] * temp[0]);
			result2 += (temp[1] * temp[1]);
		}
		return result1 * result2;
	}

	// 点乘法
	private static double pointMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		Set<Character> keySet = paramMap.keySet();
		for (Character character : keySet) {
			int temp[] = paramMap.get(character);
			result += (temp[0] * temp[1]);
		}
		return result;
	}

	public static void main(String[] args) {
		String s1 = "2017-";
		String s2 = "2017-05-06";
		System.out.println(Similarity.sim(s1,s2));
	}
}
