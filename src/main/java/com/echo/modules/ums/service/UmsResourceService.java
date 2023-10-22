package com.echo.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsResourceService extends IService<UmsResource> {
    /**
     * 添加资源
     */
    Result createResource(UmsResource umsResource);

    /**
     * 修改资源
     */
    Result updateResource(Long id, UmsResource umsResource);

    /**
     * 删除资源
     */
    Result delResource(Long id);

    /**
     * 分页查询资源
     */
    Result<Page<UmsResource>> getPageResourceList(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
