package com.hifo.dataoperation.util;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务中的楼盘，推荐合并
 *
 * @author whc
 * @date 2019/6/20 17:02
 */
public class RecommendMerge {
    private String idKey;
    private String districtKey;
    private String stdNameKey;
    private String stdAddressKey;

    public RecommendMerge(String idKey, String districtKey, String stdNameKey, String stdAddressKey) {
        this.idKey = idKey;
        this.districtKey = districtKey;
        this.stdNameKey = stdNameKey;
        this.stdAddressKey = stdAddressKey;
    }

    /**
     * 使用获客宝的数据结构
     */
    public static RecommendMerge HKB_STYLE = new RecommendMerge("id", "districtName", "stdName", "addrDetail");
    /**
     * 使用ES的数据结构
     */
    public static RecommendMerge ES_STYLE = new RecommendMerge("_id", "district", "stdName", "stdAddress");

    /**
     * 把楼盘列表转换成推荐合并的格式
     *
     * @param mapList 楼盘列表
     * @return [["主楼盘","合并到主楼盘的楼盘:楼盘分数:地址分数:坐标分数"],[]]
     */
    public List<List<String>> recommend(List<Map<String, Object>> mapList) {
        // key=目标楼盘EsID, value=需要合并到目标楼盘的EsID集合
        List<List<Map<String, Object>>> mergedList = new ArrayList<>();
        // 遍历
        for (Map<String, Object> map : mapList) {
            // 楼盘名称为空，舍弃
            if (StringUtil.isNull(map.get(stdNameKey))) {
                continue;
            }
            // 该map是否和mergedList中的一个可以合并
            int mergeIndex = getMergeIndex(mergedList, map);
            if (mergeIndex == -1) {
                List<Map<String, Object>> list = new ArrayList<>();
                list.add(map);
                mergedList.add(list);
            } else {
                mergedList.get(mergeIndex).add(map);
            }
        }
        // 把mergedList转换成只包含id的数据，然后传给获客宝
        List<List<String>> resultList = new ArrayList<>();
        for (List<Map<String, Object>> list : mergedList) {
            // 如果只有一个，忽略（因为不需要合并）
            if (list.size() < 2) {
                continue;
            }
            List<String> strList = new ArrayList<>();
            Map<String, Object> basicMap = list.get(0);
            for (Map<String, Object> map : list) {
                // 计算分数
                double nameScore = nameScore(StringUtil.str(basicMap.get(stdNameKey)), StringUtil.str(map.get(stdNameKey)));
                double addressScore;
                try {
                    addressScore = addressScore(JSONUtil.obj2Map(JSONArray.parseArray(basicMap.get(stdAddressKey).toString()).get(0)),
                            JSONUtil.obj2Map(JSONArray.parseArray(map.get(stdAddressKey).toString()).get(0)));
                } catch (Exception ignored) {
                    addressScore = 0;
                }
                if (nameScore == 0 && addressScore == 0) {
                    continue;
                }
                // id后面为3个分数，分别为：名称、地址、坐标
                strList.add(map.get(idKey).toString()
                        + ":" + new BigDecimal(nameScore).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()
                        + ":" + new BigDecimal(addressScore).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()
                        + ":" + 0);
            }
            resultList.add(strList);
        }
        // 打印
        return resultList;
    }

    /**
     * 判断当前楼盘是否可以合并到已有的List中
     *
     * @param mergedList 已有的List
     * @param map        当前楼盘
     * @return 可以合并的楼盘的下标
     */
    private int getMergeIndex(List<List<Map<String, Object>>> mergedList, Map<String, Object> map) {
        for (int i = 0; i < mergedList.size(); i++) {
            List<Map<String, Object>> group = mergedList.get(i);
            for (Map<String, Object> mapInGroup : group) {
                // 首先必须是一个行政区
                String district1 = StringUtil.str(mapInGroup.get(districtKey));
                String district2 = StringUtil.str(map.get(districtKey));
                if (!district1.equals(district2)) {
                    continue;
                }

                // 判断名称
                String name1 = StringUtil.str(mapInGroup.get(stdNameKey));
                String name2 = StringUtil.str(map.get(stdNameKey));

                if (name1.length() > 2 && name2.length() > 2) {
                    if (name1.contains(name2) || name2.contains(name1)) {
                        return i;
                    }
                }

                // 判断拼音
                String pinyin1 = StringUtil.toPinyin(name1);
                String pinyin2 = StringUtil.toPinyin(name2);
                if (pinyin1.contains(pinyin2) || pinyin2.contains(pinyin1)) {
                    return i;
                }

                // 判断地址
                try {
                    Map<String, Object> address1 = JSONUtil.obj2Map(JSONArray.parseArray(mapInGroup.get(stdAddressKey).toString()).get(0));
                    Map<String, Object> address2 = JSONUtil.obj2Map(JSONArray.parseArray(map.get(stdAddressKey).toString()).get(0));
                    if (StringUtil.isNull(address1.get("road")) || StringUtil.isNull(address1.get("number"))
                            || StringUtil.isNull(address2.get("road")) || StringUtil.isNull(address2.get("number"))) {
                        continue;
                    }
                    if (address1.get("road").equals(address2.get("road")) &&
                            address1.get("number").equals(address2.get("number"))) {
                        return i;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return -1;
    }

    /**
     * 计算两个名称的相似度
     *
     * @param name1 基准值
     * @param name2 比较值
     * @return 分数
     */
    private double nameScore(String name1, String name2) {
        String pinyin1 = StringUtil.toPinyin(name1);
        String pinyin2 = StringUtil.toPinyin(name2);
        // 完全相等，基准分：100
        if (name1.equalsIgnoreCase(name2)) {
            return 100d;
        }

        // 如果拼音相等，基准分：95
        if (pinyin1.equals(pinyin2)) {
            return 95d;
        }

        // 如果一个字符串以另一个字符串开头，基准分：90
        if (name1.startsWith(name2) || name2.startsWith(name1) || pinyin1.startsWith(pinyin2) || pinyin2.startsWith(pinyin1)) {
            double len = Math.abs(name1.length() - name2.length());
            // 附加分：10，根据长度差确定附加分的值
            double point = 10 * (1 - len / name1.length());
            // 基准分 + 附加分, 附加分不能<0
            return 90d + (point < 0 ? 0 : point);
        }

        // 如果一个字符串包含另一个，基准分：80
        if (name1.contains(name2) || name2.contains(name1) || pinyin1.contains(pinyin2) || pinyin2.contains(pinyin1)) {
            double len = Math.abs(name1.length() - name2.length());
            // 附加分：20，根据长度差确定附加分的值
            double point = 20 * (1 - len / name1.length());
            // 基准分 + 附加分, 附加分不能<0
            return 80d + (point < 0 ? 0 : point);
        }

        // 如果以上都不是，使用编辑距离，基准分：0 （但是理论上不会走这个流程）
        double len = getEditDistance(name1, name2);
        // 附加分：100
        double point = 100 * (1 - len / name1.length());
        return 0d + (point < 0 ? 0 : point);
    }

    /**
     * 获取编辑距离
     *
     * @param str1 字符串1
     * @param str2 字符串2
     */
    private int getEditDistance(String str1, String str2) {
        if (str1.equals(str2)) {
            return 0;
        }
        /* dp[i][j]表示源串str1位置i到目标串str2位置j处最低需要操作的次数 */
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 1; i <= str1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= str2.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1));
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }

    /**
     * 判断两个地址之间的分数
     *
     * @param address1 基准值
     * @param address2 比较值
     * @return 分数
     */
    private double addressScore(Map<String, Object> address1, Map<String, Object> address2) {
        if (address1 == null || address2 == null) {
            return 0;
        }
        if (StringUtil.isNull(address1.get("road")) || StringUtil.isNull(address2.get("road"))) {
            return 0;
        }
        if (!address1.get("road").equals(address2.get("road"))) {
            return 0;
        }
        try {
            double point = 100;
            int number1 = Integer.parseInt(address1.get("number").toString());
            int number2 = Integer.parseInt(address2.get("number").toString());
            // 相差一个号，减少3分
            point = point - Math.abs(number1 - number2) * 3;
            return point < 0 ? 0 : point;
        } catch (Exception ignored) {
            return 30;
        }
    }
}