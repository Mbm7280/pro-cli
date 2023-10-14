package com.echo.modules.user.service;

import com.echo.config.api.Result;
import com.echo.modules.user.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-14
 */
public interface UserService extends IService<User> {

    /**
     * 获取所有用户
     * @return
     */
    Result<List<User>> getAllUsers();

}
