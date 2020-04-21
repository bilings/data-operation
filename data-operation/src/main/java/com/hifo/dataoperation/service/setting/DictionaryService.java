package com.hifo.dataoperation.service.setting;

import com.hifo.dataoperation.entity.BusDictionary;
import com.hifo.dataoperation.vo.DictVO;

import java.util.List;

/**
 * 字典操作Service
 *
 * @author xmw
 * @date 2019/4/28 18:04
 */
public interface DictionaryService  {

    /**
     * 根据分类查询字典数据
     *
     * @param categoryId       分类id
     * @param categoryName 分类
     * @return List
     */
    List<BusDictionary> query(Long categoryId, String categoryName);



    /**
     * 查询字典表所有数据
     *
     * @return List
     */
    List<BusDictionary> query();

    /**
     * 把id转换成对应的text
     *
     * @param id 输入id
     * @return String
     */
    String id2Text(String id);

    /**
     * 把id转换成对应的text
     *
     * @param ids 输入ids
     * @return String
     */
    String ids2Text(String ids);

    /**
     * 把text转换成对应的id
     *
     * @param texts    输入text
     * @param category
     * @return id
     * @throws Exception 错误的text
     */
    String texts2Id(String texts, String category) throws Exception;

    /**
     * 所有字典数据,字典项字符串
     *
     * @return
     */
    List<DictVO> dictItemsList();
}
