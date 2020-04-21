package com.hifo.dataoperation.mapper.setting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.entity.BusDictionary;
import com.hifo.dataoperation.vo.DictVO;
import com.hifo.dataoperation.vo.BusDictionaryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表接口
 *
 * @author whc
 * @date 2019/3/13 15:32
 */
public interface HfDictionaryMapper extends BaseMapper<BusDictionaryVO> {

    /**
     * 查特定分类下的字典数据
     *
     * @param id       字典分类id
     * @param category 字典分类名
     * @return List
     */
    List<BusDictionary> query(@Param("id") Long id, @Param("category") String category);

    /**
     * 特定分类下的字典数据-用于别名取用
     *
     * @param id       字典分类id
     * @param category 字典分类名
     * @return List
     */
    List<BusDictionaryVO> queryTitle(@Param("id") Long id, @Param("category") String category);

    /**
     * 所有字典数据,字典项字符串
     *
     * @return
     */
    List<DictVO> selectListByItems();
}
