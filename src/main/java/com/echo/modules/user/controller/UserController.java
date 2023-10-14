package com.echo.modules.user.controller;


import com.echo.config.annos.WebLogAnno;
import com.echo.config.api.Result;
import com.echo.modules.user.model.User;
import com.echo.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Echo
 * @since 2023-10-14
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/getAllUsers")
    @ApiOperation("获取所有用户")
    @WebLogAnno(description = "获取所有用户")
    public Result<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

}

