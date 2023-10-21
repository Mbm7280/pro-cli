package com.echo.config.security.component;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 11:35
 * 项目名称: {cli}
 * 文件名称: IgnoreUrlsHandler
 * 文件描述: [白名单资源路径]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
@Data
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsHandler {

    private List<String> urls = new ArrayList<>();

}
