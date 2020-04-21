package com.hifo.dataoperation.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hifo.dataoperation.base.Entity;
import com.hifo.dataoperation.entity.CustomerUser;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.entity.Module;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用Service，包含：日志、获取序列号
 *
 * @author xmw
 * @date 2019/4/28 16:08
 */
public interface BasicService extends IService<T> {

    /**
     * 获取当前登录用户
     *
     * @return ExtEmployee
     */
    ExtEmployee getLoginEmployee();

    /**
     * 获取当前微信登录用户 openid
     *
     * @param request
     * @return CustomerUser
     */
    CustomerUser getLoginCustomerUserByOpenid(HttpServletRequest request);

    /**
     * 获取当前用户的权限，用户未登录时返回new ArrayList
     * 因此调用该方法获取权限不会有空指针异常
     *
     * @return list
     */
    List getLoginPermission();

    /**
     * 获取模块，并构造数据结构
     *
     * @return Map
     */
    Map<Module, Set<Module>> getModules();

    /**
     * 记录操作日志
     *
     * @param table  更新的表
     * @param pk     主键
     * @param object 本次更新对象
     */
    void updateLog(Table table, Field pk, Object object);

    /**
     * 记录操作日志
     *
     * @param table  更新的表
     * @param pk     主键
     * @param object 本次更新对象
     * @param emp    当前用户
     */
    void updateLogEmployee(Table table, Field pk, Object object, ExtEmployee emp);

    /**
     * 获取下一个序列号
     *
     * @param sequence 序列号名称
     * @return long
     */
    long nextVal(Sequence sequence);

    /**
     * 获取下一个序列号并检查重复
     *
     * @param table    使用该序列号的表
     * @param sequence 序列号名称
     * @return long
     */
    long nextVal(Table table, Sequence sequence);

    /**
     * 查询表的数据，返回map
     *
     * @param table 表
     * @param pk    主键
     * @return Map
     */
    Map<String, Object> load2Map(Table table, Field pk);

    /**
     * 查询表的数据，返回java对象
     *
     * @param table 表
     * @param pk    主键
     * @param clazz Java对象
     * @param <T>   泛型
     * @return T
     */
    <T> T load2Java(Table table, Field pk, Class<T> clazz);

    /**
     * 查询表的数据，返回java对象
     *
     * @param table  表
     * @param fields 条件
     * @param clazz  Java对象
     * @param <T>    泛型
     * @return List
     */
    <T> List<T> query2JavaWithOr(Table table, Class<T> clazz, Field... fields);

    /**
     * 查询表的数据，返回java对象
     *
     * @param table  表
     * @param fields 条件
     * @param clazz  Java对象
     * @param <T>    泛型
     * @return List
     */
    <T> List<T> query2JavaWithAnd(Table table, Class<T> clazz, Field... fields);

    /**
     * 通用保存方法
     *
     * @param table  表名
     * @param entity 被保存的对象
     * @return Long
     */
    Long update(Table table, Entity entity);

    /**
     * 通用保存方法
     * 自动判断是新增或者更新
     * 新增不记录日志，更新记录日志
     *
     * @param table    表名
     * @param entity   被保存的对象
     * @param sequence 使用的序列号
     * @return 保存/更新行的序列号
     */
    Long update(Table table, Entity entity, Sequence sequence);

    /**
     * select count(*) from table where ...
     *
     * @param tableName 表名
     * @param fields    条件
     * @return long
     */
    long countWithAnd(Table tableName, Field... fields);

    /**
     * select count(*) from table where ...
     *
     * @param tableName 表名
     * @param fields    条件
     * @return long
     */
    long countWithOr(Table tableName, Field... fields);

    /**
     * 根据主键删除
     *
     * @param tableName 表名
     * @param pk        主键
     * @return 删除的数量
     */
    int delete(Table tableName, Field pk);
}
