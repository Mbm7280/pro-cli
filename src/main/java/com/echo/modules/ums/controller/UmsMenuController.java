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
    @PostMapping(value = "/addMenu")
    public Result addMenu(@RequestBody UmsMenu umsMenu) {
        return menuService.addMenu(umsMenu);
    }

    @ApiOperation("修改菜单")
    @PutMapping(value = "/updateMenu")
    public Result updateMenu(@RequestBody UmsMenu umsMenu) {
        return menuService.updateMenu(umsMenu);
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping(value = "getMenuById/{menuId}")
    public Result<UmsMenu> getMenuById(@PathVariable Long menuId) {
        return menuService.getMenuById(menuId);
    }

    @ApiOperation("根据ID删除菜单")
    @DeleteMapping(value = "/delMenu/{menuId}")
    public Result delMenu(@PathVariable Long menuId) {
        return menuService.delMenu(menuId);

    }


    @ApiOperation("分页查询菜单")
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
    @GetMapping(value = "/updateHidden/{menuId}")
    public Result updateHidden(@PathVariable Long menuId) {
        return menuService.updateMenuHidden(menuId);
    }

}

