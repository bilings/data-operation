package com.hifo.dataoperation.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hifo.dataoperation.entity.ExtEmployee;
import com.hifo.dataoperation.mapper.base.Field;
import com.hifo.dataoperation.mapper.base.Sequence;
import com.hifo.dataoperation.mapper.base.Table;
import com.hifo.dataoperation.mapper.basic.BasicMapper;
import com.hifo.dataoperation.service.base.BasicService;
import com.hifo.dataoperation.util.ReflexUtil;
import com.hifo.dataoperation.util.StringUtil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 重写mybatisPlus方法，对修改方法添加日志、更新用户信息等操作
 *
 * @author jinzhichen
 * @date 2019/4/12 15:22
 */
public class MybatisPlusService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Autowired
    private BasicService basicService;

    @Autowired
    private BasicMapper basicMapper;

    /**
     * 单个对象是否添加日志
     */
    public void insertLogObject(Object o, ExtEmployee emp) {
        if (isIncludeId(o)) {
            addLog(o, emp);
        }
    }

    /**
     * 添加日志
     */
    public void addLog(Object o, ExtEmployee emp) {
        Field pk = Field.pk(obj2Map(o).get("id") + "");
        try {
            String tableName = getTableName(Class.forName(o.getClass().getName())).get("tableName");
            //添加日志
            basicService.updateLogEmployee(Table.valueOf(tableName), pk, o, emp);
        } catch (ClassNotFoundException e) {
        }
    }

    public boolean insert(T t, Sequence sequence, Table table, ExtEmployee emp) {
        return super.save(setUserInfoObject(t, sequence, table, emp));
    }

    public boolean updateById(T entity, ExtEmployee emp) {
        insertLogObject(entity, emp);
        return super.updateById(setUserInfoObject(entity, emp));
    }

    public boolean insert(T t, Sequence sequence, Table table) {
        return super.save(setUserInfoObject(t, sequence, table));
    }

    public boolean insertBatch(Collection<T> entityList, Sequence sequence, Table table) {
        return super.saveBatch(setUserInfoList(entityList, sequence, table));
    }

    public boolean save(T o, ExtEmployee emp) {
        return super.save(setUserInfoObject(o, emp));
    }

    @Override
    public boolean save(T o) {
        return super.save(setUserInfoObject(o));
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        return super.saveBatch(setUserInfoObjects(entityList));
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return super.saveBatch(setUserInfoObjects(entityList), batchSize);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        insertLogList(entityList);
        return super.saveOrUpdateBatch(setUserInfoObjects(entityList));
    }

    public boolean saveOrUpdateBatch(Collection<T> entityList, Sequence sequence, Table table) {
        insertLogList(entityList);
        return super.saveOrUpdateBatch(setUserInfoList(entityList, sequence, table));
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        insertLogList(entityList);
        return super.saveOrUpdateBatch(setUserInfoObjects(entityList), batchSize);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean remove(Wrapper<T> wrapper) {
        return super.remove(wrapper);
    }

    @Override
    public boolean updateById(T entity) {
        insertLogObject(entity);
        return super.updateById(setUserInfoObject(entity));
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        List entityList = list(updateWrapper);
        insertLogList(entityList);
        return super.update(setUserInfoObject(entity), updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        insertLogList(entityList);
        return super.updateBatchById(setUserInfoObjects(entityList));
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        insertLogList(entityList);
        return super.updateBatchById(setUserInfoObjects(entityList), batchSize);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        insertLogObject(entity);
        return super.saveOrUpdate(setUserInfoObject(entity));
    }

    @Override
    public T getById(Serializable serializable) {
        return super.getById(serializable);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return super.getMap(queryWrapper);
    }

    @Override
    public Object getObj(Wrapper<T> queryWrapper) {
        return super.getObj(queryWrapper);
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return super.count(queryWrapper);
    }

    @Override
    public List list(Wrapper<T> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public IPage page(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.page(page, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return super.listMaps(queryWrapper);
    }

    @Override
    public List<Object> listObjs(Wrapper<T> queryWrapper) {
        return super.listObjs(queryWrapper);
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }

    @Override
    public Collection listByMap(Map<String, Object> columnMap) {
        return super.listByMap(columnMap);
    }

    @Override
    public Collection listByIds(Collection<? extends Serializable> idList) {
        return super.listByIds(idList);
    }

    @Override
    public boolean removeByIds(Collection collection) {
        return super.removeByIds(collection);
    }

    @Override
    public boolean removeByMap(Map<String, Object> columnMap) {
        return super.removeByMap(columnMap);
    }

    /**
     * 通过获取类上的@TableName注解获取表名称
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getTableName(Class<?> clazz) {
        Map<String, String> map = new ConcurrentHashMap<>(2);
        TableName annotation = clazz.getAnnotation(TableName.class);
        String name = annotation.value();
        String className = clazz.getSimpleName();
        map.put("tableName", name);
        map.put("className", className);
        return map;
    }

    /**
     * 添加日志
     */
    public void addLog(Object o) {
        Field pk = Field.pk(obj2Map(o).get("id") + "");
        try {
            String tableName = getTableName(Class.forName(o.getClass().getName())).get("tableName");
            //添加日志
            basicService.updateLog(Table.valueOf(tableName), pk, o);
        } catch (ClassNotFoundException e) {
        }
    }

    /**
     * Object 转map
     */
    public Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map = new HashMap<>(0);
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        java.lang.reflect.Field[] fieldsSuper = obj.getClass().getSuperclass().getDeclaredFields();
        for (java.lang.reflect.Field field : fieldsSuper) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (obj.getClass().getSuperclass().getSuperclass() != null) {
            java.lang.reflect.Field[] fieldsSuperParent = obj.getClass().getSuperclass().getSuperclass().getDeclaredFields();
            for (java.lang.reflect.Field field : fieldsSuperParent) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * 判断单个对象是否包含id
     */
    public boolean isIncludeId(Object obj) {
        Field pk = Field.pk(obj2Map(obj).get("id") + "");
        String tableName = "";
        try {
            tableName = getTableName(Class.forName(obj.getClass().getName())).get("tableName");
        } catch (ClassNotFoundException e) {
        }
        // 获取保存前和保存后的Map
        Map<String, Object> oldMap = basicMapper.load(Table.valueOf(tableName), pk);
        if (null == oldMap) {
            return false;
        }
        if (null != obj2Map(obj).get("id") && !"0".equals(obj2Map(obj).get("id") + "")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * list 是否添加日志
     */
    public void insertLogList(Collection list) {
        for (Object o : list) {
            if (isIncludeId(o)) {
                addLog(o);
            }
        }
    }

    /**
     * 单个对象是否添加日志
     */
    public void insertLogObject(Object o) {
        if (isIncludeId(o)) {
            addLog(o);
        }
    }

    /**
     * 列表对象是否添加用户信息 主键序列化
     */
    public Collection<T> setUserInfoList(Collection<T> list, Sequence sequence, Table table) {
        Collection<T> collection = new ArrayList<>();
        for (T obj : list) {
            collection.add(setUserInfoObject(obj, sequence, table));
        }
        return collection;
    }

    /**
     * 列表对象是否添加用户信息 主键自增
     */
    public Collection<T> setUserInfoObjects(Collection<T> list) {
        Collection<T> collection = new ArrayList<>();
        for (T obj : list) {
            collection.add(setUserInfoObject(obj));
        }
        return collection;
    }

    /**
     * 单个对象是否添加用户信息 主键自增
     *
     * @param t
     * @return
     */
    public T setUserInfoObject(T t) {
        var emp = basicService.getLoginEmployee();
        return setUserInfoObject(t, emp);
    }

    /**
     * 单个对象是否添加用户信息 主键自增
     */
    public T setUserInfoObject(T t, ExtEmployee emp) {
        if(emp == null){
            return null;
        }
        if (!isIncludeId(t)) {
            // 设置创建人信息
            ReflexUtil.callMethod(t, "setCreateId", emp.getId().intValue());
            ReflexUtil.callMethod(t, "setCreateName", emp.getName());
            ReflexUtil.callMethod(t, "setOrganizationId", emp.getOrganizationId().intValue());
        }
        // 如果有updateTime属性，设置为空，数据库会自动更新
        ReflexUtil.callMethod(t, "setUpdateTime", "");
        // 如果有updateId和updateName属性，则设置为当前人
        ReflexUtil.callMethod(t, "setUpdateId", emp.getId().intValue());
        ReflexUtil.callMethod(t, "setUpdateName", emp.getName());
        return t;
    }

    /**
     * 单个对象是否添加用户信息 主键序列化
     */
    public T setUserInfoObject(T t, Sequence sequence, Table table) {
        var emp = basicService.getLoginEmployee();
        return setUserInfoObject(t, sequence, table, emp);
    }

    /**
     * 单个对象是否添加用户信息 主键序列化
     */
    public T setUserInfoObject(T t, Sequence sequence, Table table, ExtEmployee emp) {
        if (!isIncludeId(t) || sequence != null) {
            if (sequence != null && StringUtil.isNull(ReflexUtil.callMethodValue(t, "getId"))) {
                long id = basicService.nextVal(table, sequence);
                ReflexUtil.callMethod(t, "setId", id);
            }
            // 设置创建人信息
            ReflexUtil.callMethod(t, "setCreateId", emp.getId().intValue());
            ReflexUtil.callMethod(t, "setCreateName", emp.getName());
            ReflexUtil.callMethod(t, "setOrganizationId", emp.getOrganizationId().intValue());
        }
        // 如果有updateTime属性，设置为空，数据库会自动更新
        ReflexUtil.callMethod(t, "setUpdateTime", "");
        // 如果有updateId和updateName属性，则设置为当前人
        ReflexUtil.callMethod(t, "setUpdateId", emp.getId().intValue());
        ReflexUtil.callMethod(t, "setUpdateName", emp.getName());
        return t;
    }

}
