package com.echo.modules.ums.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.req.LoginReqDTO;
import com.echo.modules.ums.dto.req.RegisterReqDTO;
import com.echo.modules.ums.dto.req.UpdateUserPasswordReqDTO;
import com.echo.modules.ums.dto.res.LoginResDTO;
import com.echo.modules.ums.dto.res.RefreshTokenResDTO;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.model.UmsUser;
import com.echo.modules.ums.service.UmsRoleService;
import com.echo.modules.ums.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.echo.config.api.ResultCode.THE_AUTHORIZED_FAILED;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@RestController()
@RequestMapping("/umsUser")
@Api(tags = "UmsUserController")
@Tag(name = "UmsUserController", description = "用户管理")
public class UmsUserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsUserService userService;

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public Result<UmsUser> register(@Validated @RequestBody RegisterReqDTO registerReqDTO) {
        return userService.register(registerReqDTO);
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public Result<LoginResDTO> login(@Validated @RequestBody LoginReqDTO loginReqDTO) {
        return userService.login(loginReqDTO);
    }


    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public Result<RefreshTokenResDTO> refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(Principal principal) {
        if (principal == null) {
            return Result.failed(THE_AUTHORIZED_FAILED);
        }
        String username = principal.getName();
        UmsUser umsUser = userService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsUser.getUsername());
        data.put("menus", roleService.getMenuListByUserId(umsUser.getId()));
        data.put("icon", umsUser.getIcon());
        List<UmsRole> roleList = userService.getRoleListByUserId(umsUser.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getRoleName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return Result.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    public Result logout() {
        return Result.success();
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/getPageUserListByKeyword")
    public Result<Page<UmsUser>> getPageUserListByKeyword(@RequestParam(value = "keyword", required = false) String keyword,
                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        return userService.getPageUserListByKeyword(keyword, pageSize, pageNum);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/getUserInfoByUserId/{userId}")
    public Result<UmsUser> getUserInfoByUserId(@PathVariable Long userId) {
        UmsUser userInfo = userService.getById(userId);
        return Result.success(userInfo);
    }

    @ApiOperation("修改指定用户信息")
    @PutMapping(value = "/updateUserInfoByUserId/{userId}")
    public Result updateUserInfoByUserId(@PathVariable Long userId, @RequestBody UmsUser userInfo) {
        return userService.updateUserInfoByUserId(userId, userInfo);
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping(value = "/updateUserPassword")
    public Result updateUserPassword(@Validated @RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        return userService.updateUserPassword(updateUserPasswordReqDTO);
    }

    @ApiOperation("删除指定用户信息")
    @DeleteMapping(value = "/delUserByUserId/{userId}")
    public Result delUserByUserId(@PathVariable Long userId) {
        return userService.delUserByUserId(userId);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/allowUserRole")
    public Result allowUserRole(@RequestParam("userId") Long userId,
                                @RequestParam("roleIds") List<Long> roleIds) {
        return userService.allowUserRole(userId, roleIds);
    }

}

