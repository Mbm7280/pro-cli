package com.echo.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.mapper.UmsResourceMapper;
import com.echo.modules.ums.service.UmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echo.modules.ums.service.UmsUserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.echo.config.api.ResultCode.THE_RESOURCE_ADD_FAILED;
import static com.echo.config.api.ResultCode.THE_RESOURCE_DEL_FAILED;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {

    @Autowired
    private UmsUserCacheService userCacheService;

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceServiceImpl
     * 方法名称：addResource
     * 方法描述：{ 添加资源 }
     * param：[umsResource]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:37
     * version：1.0
     */
    @Override
    public Result addResource(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return save(umsResource) ? Result.success() : Result.failed(THE_RESOURCE_ADD_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceServiceImpl
     * 方法名称：updateResourceById
     * 方法描述：{ 修改资源 }
     * param：[id, umsResource]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:38
     * version：1.0
     */
    @Override
    public Result updateResourceById(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        boolean success = updateById(umsResource);
        if (success) {
            userCacheService.delResourceListByResource(id);
        }
        return success ? Result.success() : Result.failed(THE_RESOURCE_ADD_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceServiceImpl
     * 方法名称：delResourceById
     * 方法描述：{ 删除资源 }
     * param：[id]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:40
     * version：1.0
     */
    @Override
    public Result delResourceById(Long id) {
        boolean success = removeById(id);
        if (success) {
            userCacheService.delResourceListByResource(id);
        }
        return success ? Result.success() : Result.failed(THE_RESOURCE_DEL_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceServiceImpl
     * 方法名称：getPageResourceList
     * 方法描述：{ 分页查询资源 }
     * param：[categoryId, nameKeyword, urlKeyword, pageSize, pageNum]
     * return：com.echo.config.api.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.echo.modules.ums.model.UmsResource>>
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:42
     * version：1.0
     */
    @Override
    public Result<Page<UmsResource>> getPageResourceList(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Page<UmsResource> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsResource> lambda = wrapper.lambda();
        if (categoryId != null) {
            lambda.eq(UmsResource::getCategoryId, categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)) {
            lambda.like(UmsResource::getName, nameKeyword);
        }
        if (StrUtil.isNotEmpty(urlKeyword)) {
            lambda.like(UmsResource::getUrl, urlKeyword);
        }
        return Result.success(page(page, wrapper));
    }
}
