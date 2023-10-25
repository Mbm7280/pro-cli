package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.echo.config.api.Result;
import com.echo.config.exception.ApiException;
import com.echo.modules.ums.model.UmsResourceCategory;
import com.echo.modules.ums.mapper.UmsResourceCategoryMapper;
import com.echo.modules.ums.service.UmsResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.echo.common.constant.CommonConstant.ONE;
import static com.echo.common.constant.CommonConstant.ZERO;
import static com.echo.config.api.ResultCode.*;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper, UmsResourceCategory> implements UmsResourceCategoryService {

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceCategoryServiceImpl
     * 方法名称：getAllResourceCategories
     * 方法描述：{ 获取所有资源分类 }
     * param：[]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.model.UmsResourceCategory>>
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:17
     * version：1.0
     */
    @Override
    public Result<List<UmsResourceCategory>> getAllResourceCategories() {
        List<UmsResourceCategory> umsResourceCategories = list(new LambdaQueryWrapper<UmsResourceCategory>().eq(UmsResourceCategory::getStatus, ONE).orderByDesc(UmsResourceCategory::getSort));
        return CollUtil.isEmpty(umsResourceCategories) ? Result.failed(THE_RESOURCE_CATEGORY_QUERY_FAILED) : Result.success(umsResourceCategories);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceCategoryServiceImpl
     * 方法名称：addResourceCategory
     * 方法描述：{ 创建资源分类 }
     * param：[umsResourceCategory]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/22 15:21
     * version：1.0
     */
    @Override
    public Result addResourceCategory(UmsResourceCategory umsResourceCategory) {
        if (umsResourceCategory.getStatus() == ZERO) {
            umsResourceCategory.setStatus(ONE);
        }
        umsResourceCategory.setCreateTime(new Date());
        return save(umsResourceCategory) ? Result.success() : Result.failed(THE_RESOURCE_CATEGORY_ADD_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceCategoryServiceImpl
     * 方法名称：updateResourceCategory
     * 方法描述：{ 修改资源分类 }
     * param：[umsResourceCategory]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/25 21:01
     * version：1.0
     */
    @Override
    public Result updateResourceCategory(UmsResourceCategory umsResourceCategory) {
        getUmsResourceCategoryById(umsResourceCategory.getId());
        if (umsResourceCategory.getStatus() == ZERO) {
            umsResourceCategory.setStatus(ONE);
        }
        return updateById(umsResourceCategory) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceCategoryServiceImpl
     * 方法名称：delResourceCategory
     * 方法描述：{ 根据ID删除后台资源 }
     * param：[resourceCategoryId]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/25 21:14
     * version：1.0
     */
    @Override
    public Result delResourceCategory(Long resourceCategoryId) {
        UmsResourceCategory umsResourceCategory = getUmsResourceCategoryById(resourceCategoryId);
        umsResourceCategory.setStatus(ZERO);
        return updateById(umsResourceCategory) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsResourceCategoryServiceImpl
     * 方法名称：getUmsResourceCategoryById
     * 方法描述：{ 根据resourceCategoryId获取资源 }
     * param：[resourceCategoryId]
     * return：com.echo.modules.ums.model.UmsResourceCategory
     * 创建人：@author Echo
     * 创建时间：2023/10/25 21:08
     * version：1.0
     */
    private UmsResourceCategory getUmsResourceCategoryById(Long resourceCategoryId) {
        UmsResourceCategory resourceCategory = getById(resourceCategoryId);
        if (ObjectUtil.isEmpty(resourceCategory)) {
            throw new ApiException(THE_RESOURCE_QUERY_FAILED);
        }
        return resourceCategory;
    }

}
