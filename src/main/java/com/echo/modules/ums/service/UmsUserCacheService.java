package com.echo.modules.ums.service;

import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsUser;

import java.util.List;

public interface UmsUserCacheService {

    /**
     * 获取缓存后台用户信息
     */
    UmsUser getAdmin(String username);


    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsUser umsUser);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置后台后台用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);


}
