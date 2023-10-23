package com.echo.modules.ums.controller;


import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsResourceCategory;
import com.echo.modules.ums.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/umsResourceCategory")
@Api(tags = "UmsResourceCategoryController")
@Tag(name = "UmsResourceCategoryController", description = "后台资源分类管理")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/getAllResourceCategories")
    public Result<List<UmsResourceCategory>> getAllResourceCategories() {
        return resourceCategoryService.getAllResourceCategories();
    }

    @ApiOperation("添加资源分类")
    @PostMapping(value = "/addResourceCategory")
    public Result addResourceCategory(@RequestBody UmsResourceCategory umsResourceCategory) {
        return resourceCategoryService.addResourceCategory(umsResourceCategory);
    }


    @ApiOperation("修改后台资源分类")
    @PutMapping(value = "/updateResourceCategory/{id}")
    public Result updateResourceCategory(@PathVariable Long id,
                                         @RequestBody UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        return resourceCategoryService.updateById(umsResourceCategory) ? Result.success() : Result.failed();
    }

    @ApiOperation("根据ID删除后台资源")
    @DeleteMapping(value = "/delResourceCategory/{id}")
    public Result delete(@PathVariable Long id) {
        return resourceCategoryService.removeById(id) ? Result.success() : Result.failed();
    }


}

