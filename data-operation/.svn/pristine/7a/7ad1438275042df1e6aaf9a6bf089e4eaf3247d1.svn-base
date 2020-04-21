package com.hifo.dataoperation.mapper.basic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Table;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 单标的通用处理
 *
 * @author whc
 * @date 2019/3/13 16:17
 */
public interface BasicMapper extends BaseMapper<T> {

    /**
     * 根据主键获取数据库对象
     *
     * @param tableName 表名
     * @param pk        主键
     * @return Map
     */
    Map<String, Object> load(@Param("tableName") Table tableName, @Param("pk") Field pk);

    /**
     * 通用查询接口：全部
     *
     * @param tableName 表名
     * @param fields    查询条件
     * @return List
     */
    List<Map<String, Object>> queryWithOr(@Param("tableName") Table tableName, @Param("fields") Field... fields);

    /**
     * 通用查询接口：全部
     *
     * @param tableName 表名
     * @param fields    查询条件
     * @return List
     */
    List<Map<String, Object>> queryWithAnd(@Param("tableName") Table tableName, @Param("fields") Field... fields);

    /**
     * 通用查询接口：分页
     *
     * @param tableName 表名
     * @param offset    数据开始行
     * @param size      数据总行数
     * @param fields    查询条件
     * @return List
     */
    List<Map<String, Object>> queryPage(@Param("tableName") Table tableName,
                                        @Param("offset") int offset,
                                        @Param("size") int size,
                                        @Param("fields") Field... fields);

    /**
     * 通用统计记录数接口
     *
     * @param tableName 表名
     * @param fields    查询条件
     * @return 记录数
     */
    long countWithAnd(@Param("tableName") Table tableName, @Param("fields") Field... fields);

    /**
     * 通用统计记录数接口
     *
     * @param tableName 表名
     * @param fields    查询条件
     * @return 记录数
     */
    long countWithOr(@Param("tableName") Table tableName, @Param("fields") Field... fields);

    /**
     * 通用新增接口
     *
     * @param tableName 表名
     * @param fields    插入的字段
     * @return 1=插入成功
     */
    int insert(@Param("tableName") Table tableName, @Param("fields") Field... fields);

    /**
     * 通用更新接口，为了安全性，只支持基于id的通用更新
     *
     * @param tableName 表名
     * @param pk        primary key
     * @param fields    更新的字段
     * @return 1=更新成功
     */
    int update(@Param("tableName") Table tableName, @Param("pk") Field pk, @Param("fields") Field... fields);

    /**
     * 通用删除接口，为了安全性，只支持基于id的通用删除
     *
     * @param tableName 表名
     * @param pk        primary key
     * @return 1=删除成功
     */
    int delete(@Param("tableName") Table tableName, @Param("pk") Field pk);
}
