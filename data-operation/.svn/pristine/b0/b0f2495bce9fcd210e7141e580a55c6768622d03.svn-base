package com.hifo.dataoperation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量断点上传VO
 *
 * @Author: xmw
 * @Date: 2019/5/16 17:34
 */
@ApiModel(value = "大文件属性对象")
@Data
public class PlUploadVO {

    /**
     * 文件原名
     */
    @ApiModelProperty(value = "文件原名", name = "name")
    private String name;
    /**
     * MD5
     */
    @ApiModelProperty(value = "MD5", name = "md5")
    private String md5;
    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", name = "size")
    private Long size;
    /**
     * 分块号
     */
    @ApiModelProperty(value = "分块号", name = "chunk")
    private Integer chunk;
    /**
     * 总分块数
     */
    @ApiModelProperty(value = "总分块数", name = "chunks")
    private Integer chunks;

}
