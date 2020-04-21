package com.hifo.dataoperation.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author: xmw
 * @Date: 2019/6/19 10:26
 */
@Data
public class DictVO {

    @Excel(name = "标准数据分类", width = 20)
    private String category;
    @Excel(name = "标准数据集合", width = 160)
    private String item;

}
