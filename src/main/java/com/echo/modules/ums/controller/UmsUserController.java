package com.echo.modules.ums.controller;


import cn.hutool.core.collection.CollUtil;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.req.LoginReqDTO;
import com.echo.modules.ums.dto.req.RegisterReqDTO;
import com.echo.modules.ums.dto.res.LoginResDTO;
import com.echo.modules.ums.dto.res.RefreshTokenResDTO;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.model.UmsUser;
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

}

