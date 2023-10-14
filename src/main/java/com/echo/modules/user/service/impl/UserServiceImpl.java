package com.echo.modules.user.service.impl;

import com.echo.config.api.Result;
import com.echo.modules.user.model.User;
import com.echo.modules.user.mapper.UserMapper;
import com.echo.modules.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<List<User>> getAllUsers() {
        List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }
}
