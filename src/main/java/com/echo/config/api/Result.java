package com.echo.config.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/14 20:01
 * 项目名称: {cli}
 * 文件名称: Result
 * 文件描述: [全局统一返回结果类]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private long code;
    private String message;
    private T data;

    /**
     * 成功返回结果(默认)
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功(自定义信息)
     *
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(IErrorCode errorCode) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(IErrorCode errorCode, T data) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 失败返回结果(默认)
     */
    public static <T> Result<T> failed() {
        return new Result<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    public static <T> Result<T> failed(String message) {
        return new Result<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果(自定义Error信息)
     *
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> failed(IErrorCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}

