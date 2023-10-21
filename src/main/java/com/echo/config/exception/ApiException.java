package com.echo.config.exception;

import com.echo.config.api.IErrorCode;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 17:31
 * 项目名称: {cli}
 * 文件名称: ApiException
 * 文件描述: [自定义API异常]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
