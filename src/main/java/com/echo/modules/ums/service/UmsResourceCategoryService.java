package com.echo.modules.ums.service;

import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsResourceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    Result<List<UmsResourceCategory>> getAllResourceCategories();


    /**
     * 创建资源分类
     */
    Result addResourceCategory(UmsResourceCategory umsResourceCategory);


    /**
     * 修改资源分类
     *
     * @param umsResourceCategory
     * @return
     */
    Result updateResourceCategory(UmsResourceCategory umsResourceCategory);


    /**
     * 根据ID删除后台资源
     *
     * @param resourceCategoryId
     * @return
     */
    Result delResourceCategory(Long resourceCategoryId);


}
