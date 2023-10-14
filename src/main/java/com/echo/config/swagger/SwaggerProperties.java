package com.echo.config.swagger;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/****************************************************
 * 创建人：@author ECHO
 * 创建时间: 2023/8/13 06:39
 * 项目名称: {EBlog}
 * 文件名称: SwaggerProperties
 * 文件描述: [Description]: Swagger自定义配置
 * version：1.0
 * All rights Reserved, Designed By ECHO
 *
 ********************************************************/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {
    /**
     * API文档生成基础路径
     */
    private String apiBasePackage;
    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 文档版本
     */
    private String version;
    /**
     * 文档联系人姓名
     */
    private String contactName;
    /**
     * 文档联系人网址
     */
    private String contactUrl;
    /**
     * 文档联系人邮箱
     */
    private String contactEmail;
}
