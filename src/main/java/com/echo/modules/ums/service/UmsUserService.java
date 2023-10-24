package com.echo.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.req.LoginReqDTO;
import com.echo.modules.ums.dto.req.RegisterReqDTO;
import com.echo.modules.ums.dto.req.UpdateUserPasswordReqDTO;
import com.echo.modules.ums.dto.res.LoginResDTO;
import com.echo.modules.ums.dto.res.RefreshTokenResDTO;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
     *
     * @param registerReqDTO
     * @return
     */
    Result<UmsUser> register(RegisterReqDTO registerReqDTO);


    /**
     * 登录
     *
     * @param loginReqDTO
     * @return
     */
    Result<LoginResDTO> login(LoginReqDTO loginReqDTO);

    /**
     * 刷新token
     *
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

    /**
     * 根据用户获取角色
     */
    List<UmsRole> getRoleListByUserId(Long userId);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Result<Page<UmsUser>> getPageUserListByKeyword(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    Result updateUserInfoByUserId(Long id, UmsUser userInfo);


    /**
     * 修改用户密码
     */
    Result updateUserPassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);


    Result delUserByUserId(@PathVariable Long id);


    /**
     * 修改用户角色关系
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional
    Result allowUserRole(Long adminId, List<Long> roleIds);

}
