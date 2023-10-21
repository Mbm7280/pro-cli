package com.echo.config.security.component;

import cn.hutool.json.JSONUtil;
import com.echo.config.api.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 11:32
 * 项目名称: {cli}
 * 文件名称: RestAuthenticationEntryPoint
 * 文件描述: [自定义返回结果：未登录或登录过期]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(Result.failed(authException.getMessage())));
        response.getWriter().flush();
    }

}
