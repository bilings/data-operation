package com.hifo.dataoperation.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: xmw
 * @Date: 2019/5/5 18:08
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TreeVO extends BaseTreeVO{

    private String title;
    private String label;
    private String url;
    private Boolean expand;
    private Boolean checked;
    private List<TreeVO> children;
    private Object obj;
}
