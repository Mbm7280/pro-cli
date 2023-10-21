package com.echo.config.security.component;

import cn.hutool.json.JSONUtil;
import com.echo.config.api.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 11:29
 * 项目名称: {cli}
 * 文件名称: RestfulAccessDeniedHandler
 * 文件描述: [自定义返回结果：没有权限访问时]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Result.failed(e.getMessage())));
        response.getWriter().flush();
    }

}
