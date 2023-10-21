package com.echo.modules.ums.service;

import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsUser;

import java.util.List;

public interface UmsAdminCacheService {

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

}
