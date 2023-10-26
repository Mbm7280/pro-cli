package com.echo.modules.ums.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/umsRole")
@Api(tags = "UmsRoleController")
@Tag(name = "UmsRoleController", description = "用户角色管理")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/getAllRoles")
    public Result<List<UmsRole>> getAllRoles() {
        return roleService.getAllRoles();
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/getPageRoleList")
    public Result<Page<UmsRole>> getPageRoleList(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return roleService.getPageRoleList(keyword, pageSize, pageNum);
    }

    @ApiOperation("添加角色")
    @PostMapping(value = "/addRole")
    public Result addRole(@RequestBody UmsRole role) {
        return roleService.addRole(role);
    }


    @ApiOperation("修改角色")
    @PutMapping(value = "/updateRoleByRoleId")
    public Result updateRoleByRoleId(@RequestBody UmsRole role) {
        return roleService.updateRoleByRoleId(role);
    }


    @ApiOperation("删除角色")
    @DeleteMapping(value = "/delRoleByRoleId/{id}")
    public Result delRoleByRoleId(@PathVariable Long id) {
        return roleService.delRoleByRoleId(id);
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delRoleBatch")
    public Result delRoleBatch(@RequestParam("ids") List<Long> ids) {
        return roleService.delRoleBatch(ids);
    }


    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/getMenusByRoleId/{roleId}")
    public Result<List<UmsMenu>> getMenusByRoleId(@PathVariable Long roleId) {
        return roleService.getMenusByRoleId(roleId);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/getResourcesByRoleId/{roleId}")
    public Result<List<UmsResource>> getResourcesByRoleId(@PathVariable Long roleId) {
        return roleService.getResourcesByRoleId(roleId);
    }


    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    public Result allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        return roleService.allocResource(roleId, resourceIds);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public Result allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        return roleService.allocMenu(roleId, menuIds);
    }

}

