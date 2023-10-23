package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsResourceCategory;
import com.echo.modules.ums.mapper.UmsResourceCategoryMapper;
import com.echo.modules.ums.service.UmsResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.echo.config.api.ResultCode.THE_RESOURCE_CATEGORY_ADD_FAILED;
import static com.echo.config.api.ResultCode.THE_RESOURCE_CATEGORY_QUERY_FAILED;

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
        QueryWrapper<UmsResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(UmsResourceCategory::getSort);
        List<UmsResourceCategory> umsResourceCategories = list(wrapper);
        if (CollectionUtil.isEmpty(umsResourceCategories)) {
            return Result.failed(THE_RESOURCE_CATEGORY_QUERY_FAILED);
        }
        return Result.success(umsResourceCategories);
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
        umsResourceCategory.setCreateTime(new Date());
        return save(umsResourceCategory) ? Result.success() : Result.failed(THE_RESOURCE_CATEGORY_ADD_FAILED);
    }
}
