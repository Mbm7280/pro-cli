package com.echo.config.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 13:39
 * 项目名称: {cli}
 * 文件名称: DynamicSecurityService
 * 文件描述: [动态权限相关业务类]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
public interface DynamicSecurityService {

    /**
     * 加载资源ANT通配符和资源对应MAP
     * @return
     */
    Map<String, ConfigAttribute> loadDataSource();

}
