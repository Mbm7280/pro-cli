package com.echo.config.exception;

import com.echo.config.api.IErrorCode;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 17:30
 * 项目名称: {cli}
 * 文件名称: Asserts
 * 文件描述: [断言处理类]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
