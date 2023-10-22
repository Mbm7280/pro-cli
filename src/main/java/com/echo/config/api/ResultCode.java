package com.echo.config.api;

import javax.annotation.PostConstruct;

public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),

    FAILED(500, "操作失败"),

    VALIDATE_FAILED(404, "参数检验失败"),

    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    FORBIDDEN(403, "没有相关权限"),

    PLEASE_APPORTION_PERMISSIONS(405, "请先分配权限"),

    RETURN_VALUE_IS_NULL(990, "返回值为空，请稍后重试"),


    THE_USER_HAS_REGISTERED(1001, "该用户已注册！"),


    THE_RESOURCE_CATEGORY_QUERY_FAILED(1002, "获取所有资源分类失败，请稍后重试！"),

    THE_RESOURCE_CATEGORY_ADD_FAILED(1003, "添加资源分类失败，请稍后重试！"),


    THE_RESOURCE_ADD_FAILED(1004, "添加资源失败，请稍后重试！"),

    THE_RESOURCE_DEL_FAILED(1005, "删除资源失败，请稍后重试！"),

    THE_RESOURCE_QUERY_FAILED(1006, "查询资源失败，请稍后重试！"),


    THE_MENU_QUERY_FAILED(1007, "查询菜单失败，请稍后重试！");

    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
