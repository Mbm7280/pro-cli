package com.echo.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.config.security.component.DynamicSecurityMetadataSource;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/umsResource")
@Api(tags = "UmsResourceController")
@Tag(name = "UmsResourceController", description = "后台资源管理")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping(value = "/addResource")
    public Result addResource(@RequestBody UmsResource umsResource) {
        Result result = resourceService.addResource(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return result;
    }

    @ApiOperation("修改后台资源")
    @PutMapping(value = "/updateResourceByID/{id}")
    public Result updateResourceById(@PathVariable Long id,
                                     @RequestBody UmsResource umsResource) {
        Result result = resourceService.updateResourceById(id, umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return result;
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/getResourceById/{id}")
    public Result<UmsResource> getResourceById(@PathVariable Long id) {
        return Result.success(resourceService.getById(id));
    }

    @ApiOperation("根据ID删除后台资源")
    @DeleteMapping(value = "/delResourceById/{id}")
    public Result delResourceById(@PathVariable Long id) {
        Result result = resourceService.delResourceById(id);
        dynamicSecurityMetadataSource.clearDataSource();
        return result;
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/getPageResourceList")
    public Result<Page<UmsResource>> getPageResourceList(@RequestParam(required = false) Long categoryId,
                                                         @RequestParam(required = false) String nameKeyword,
                                                         @RequestParam(required = false) String urlKeyword,
                                                         @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return resourceService.getPageResourceList(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
    }

}

