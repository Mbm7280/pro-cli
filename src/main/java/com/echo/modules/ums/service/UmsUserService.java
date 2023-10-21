package com.echo.modules.ums.service;

import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

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
    UmsAdminCacheService getCacheService();

}
