package com.hifo.dataoperation.mapper.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author whc
 * @date 2019/3/13 16:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    private String name;
    private String value;

    /**
     * 构造主键对象
     *
     * @param obj 主键值
     * @return Field
     */
    public static Field pk(String obj) {
        String pk = "id";
        return new Field(pk, obj);
    }

    /**
     * 构造主键对象
     *
     * @param obj 主键值
     * @return Field
     */
    public static Field pk(Integer obj) {
        String pk = "id";
        return new Field(pk, obj + "");
    }

    /**
     * 构造主键对象
     *
     * @param obj 主键值
     * @return Field
     */
    public static Field pk(Long obj) {
        String pk = "id";
        return new Field(pk, obj + "");
    }
}
