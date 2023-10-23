package com.echo.modules.ums.controller;


import cn.hutool.core.collection.CollectionUtil;
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

import static com.echo.config.api.ResultCode.THE_ROLE_QUERY_FAILED;

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

    @ApiOperation("添加角色")
    @PostMapping(value = "/addRole")
    public Result addRole(@RequestBody UmsRole role) {
        return roleService.addRole(role);
    }


    @ApiOperation("修改角色")
    @PutMapping(value = "/updateRole/{id}")
    public Result updateRole(@PathVariable Long id, @RequestBody UmsRole role) {
        role.setId(id);
        return roleService.updateById(role) ? Result.success() : Result.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delRoleBatch")
    public Result delRoleBatch(@RequestParam("ids") List<Long> ids) {
        return roleService.delRoleBatch(ids);
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/getAllRoles")
    public Result<List<UmsRole>> getAllRoles() {
        return CollectionUtil.isNotEmpty(roleService.list()) ? Result.success() : Result.failed(THE_ROLE_QUERY_FAILED);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/getPageRoleList")
    public Result<Page<UmsRole>> getPageRoleList(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return roleService.getPageRoleList(keyword, pageSize, pageNum);
    }

    @ApiOperation("修改角色状态")
    @PutMapping(value = "/updateRoleStatus/{id}")
    public Result updateRoleStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        return roleService.updateRoleStatus(id, status);
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

}

