package com.echo.config.security;

import com.echo.config.security.component.*;
import com.echo.utils.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/21 11:20
 * 项目名称: {cli}
 * 文件名称: SecurityComponents
 * 文件描述: [SpringSecurity所设计到的组件]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
@Configuration
public class SecurityComponents {

    /**
     * 密码处理
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 白名单资源路径处理器
     *
     * @return
     */
    @Bean
    public IgnoreUrlsHandler ignoreUrlsConfig() {
        return new IgnoreUrlsHandler();
    }


    /**
     * 用户无访问权限处理器
     *
     * @return
     */
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    /**
     * 未登录或登录过期处理器
     *
     * @return
     */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * token
     *
     * @return
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    /**
     * 每次请求时只执行一次的过滤器
     *
     * @return
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 访问决策管理器
     *
     * @return
     */
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    /**
     * 动态权限数据源
     *
     * @return
     */
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    /**
     * 动态权限过滤器
     *
     * @return
     */
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }


}
