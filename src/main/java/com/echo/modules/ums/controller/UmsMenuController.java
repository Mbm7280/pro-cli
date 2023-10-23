package com.echo.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.vo.UmsMenuVO;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.echo.config.api.ResultCode.THE_MENU_DELETE_FAILED;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/umsMenu")
@Api(tags = "UmsMenuController")
@Tag(name = "UmsMenuController", description = "菜单管理")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation("添加菜单")
    @PostMapping(value = "/createMenu")
    public Result createMenu(@RequestBody UmsMenu umsMenu) {
        return menuService.createMenu(umsMenu);
    }

    @ApiOperation("修改后台菜单")
    @PostMapping(value = "/updateMenu/{id}")
    public Result updateMenu(@PathVariable Long id,
                             @RequestBody UmsMenu umsMenu) {
        return menuService.updateMenu(id, umsMenu);
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping(value = "getMenuByID/{id}")
    public Result<UmsMenu> getMenuByID(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }

    @ApiOperation("根据ID删除后台菜单")
    @DeleteMapping(value = "/delMenuByID/{id}")
    public Result delMenuByID(@PathVariable Long id) {
        return menuService.removeById(id) ? Result.success() : Result.failed(THE_MENU_DELETE_FAILED);

    }


    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/getPageMenuList/{parentId}")
    public Result<Page<UmsMenu>> getPageMenuList(@PathVariable Long parentId,
                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return menuService.getPageMenuList(parentId, pageSize, pageNum);
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping(value = "/getTreesMenuList")
    public Result<List<UmsMenuVO>> getTreesMenuList() {
        return menuService.getTreesMenuList();
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping(value = "/updateHidden/{id}")
    public Result updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        return menuService.updateMenuHidden(id, hidden);
    }

}

