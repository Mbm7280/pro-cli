package com.echo.modules.ums.service;

import com.echo.config.api.Result;
import com.echo.modules.ums.dto.req.LoginReqDTO;
import com.echo.modules.ums.dto.req.RegisterReqDTO;
import com.echo.modules.ums.dto.res.LoginResDTO;
import com.echo.modules.ums.dto.res.RefreshTokenResDTO;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsUserService extends IService<UmsUser> {

    /**
     * 用户注册
     * @param registerReqDTO
     * @return
     */
    Result<UmsUser> register(RegisterReqDTO registerReqDTO);


    /**
     * 登录
     * @param loginReqDTO
     * @return
     */
    Result<LoginResDTO> login(LoginReqDTO loginReqDTO);

    /**
     * 刷新token
     * @param request
     * @return
     */
    Result<RefreshTokenResDTO> refreshToken(HttpServletRequest request);



    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);


    /**
     * 根据用户名获取后台管理员
     */
    UmsUser getAdminByUsername(String username);

    /**
     * 获取缓存服务
     */
    UmsUserCacheService getCacheService();

}
