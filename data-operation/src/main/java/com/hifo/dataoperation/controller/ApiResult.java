package com.hifo.dataoperation.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 接口返回实体
 *
 * @author whc
 * @date 2019/3/15
 */
@ApiModel
@Data
public class ApiResult<T> {
    @ApiModelProperty(value = "错误码，0成功1失败")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "返回内容")
    private T data;

    /**
     * 常量：操作成功
     */
    private static final String SUCCESS = "操作成功";

    /**
     * 常量：操作失败
     */
    private static final String FAIL = "操作失败";

    /**
     * 操作成功
     *
     * @return 泛型
     */
    public static <T> ApiResult<T> success() {
        ApiResult<T> ar = new ApiResult<>();
        ar.setCode(0);
        ar.setMsg(SUCCESS);
        return ar;
    }

    /**
     * 操作成功
     *
     * @param data 返回值
     * @return 泛型
     */
    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> ar = success();
        ar.setData(data);
        return ar;
    }

    /**
     * 操作失败
     *
     * @return 泛型
     */
    public static <T> ApiResult<T> failed() {
        ApiResult<T> ar = new ApiResult<>();
        ar.setCode(1);
        ar.setMsg(FAIL);
        return ar;
    }

    /**
     * 操作失败
     *
     * @return 泛型
     */
    public static <T> ApiResult<T> failed(String msg) {
        ApiResult<T> ar = new ApiResult<>();
        ar.setCode(1);
        ar.setMsg(msg);
        return ar;
    }
}
