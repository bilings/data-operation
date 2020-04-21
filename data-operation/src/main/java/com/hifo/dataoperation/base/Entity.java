package com.hifo.dataoperation.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 所有entity需要继承的父类，用以对增删改查进行统一处理
 * 如果不继承该类，那需要自行处理
 *
 * @author whc
 * @date 2019/3/26 16:38
 */
public class Entity {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

}
