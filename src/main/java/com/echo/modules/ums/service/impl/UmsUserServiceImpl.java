package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.config.exception.Asserts;
import com.echo.dto.AdminUserDetails;
import com.echo.modules.ums.dto.req.LoginReqDTO;
import com.echo.modules.ums.dto.req.RegisterReqDTO;
import com.echo.modules.ums.dto.req.UpdateUserPasswordReqDTO;
import com.echo.modules.ums.dto.res.LoginResDTO;
import com.echo.modules.ums.dto.res.RefreshTokenResDTO;
import com.echo.modules.ums.mapper.UmsResourceMapper;
import com.echo.modules.ums.mapper.UmsRoleMapper;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.model.UmsUser;
import com.echo.modules.ums.mapper.UmsUserMapper;
import com.echo.modules.ums.model.UmsUserRoleRelation;
import com.echo.modules.ums.service.UmsUserCacheService;
import com.echo.modules.ums.service.UmsUserRoleRelationService;
import com.echo.modules.ums.service.UmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echo.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.echo.common.constant.CommonConstant.ONE;
import static com.echo.common.constant.CommonConstant.ZERO;
import static com.echo.config.api.ResultCode.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsUserRoleRelationService adminRoleRelationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsUserServiceImpl
     * 方法名称：register
     * 方法描述：{ 用户注册 }
     * param：[registerReqDTO]
     * return：com.echo.config.api.Result<com.echo.modules.ums.model.UmsUser>
     * 创建人：@author Echo
     * 创建时间：2023/10/21 17:56
     * version：1.0
     */
    @Override
    public Result<UmsUser> register(RegisterReqDTO registerReqDTO) {
        UmsUser umsAdmin = new UmsUser();
        BeanUtils.copyProperties(registerReqDTO, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(ONE);
        // 查询是否有相同用户名的用户
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, umsAdmin.getUsername());
        List<UmsUser> umsAdminList = list(wrapper);
        if (umsAdminList.size() > ZERO) {
            return Result.success(THE_USER_HAS_REGISTERED);
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        return baseMapper.insert(umsAdmin) > ZERO ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsUserServiceImpl
     * 方法名称：login
     * 方法描述：{ 登录 }
     * param：[loginReqDTO]
     * return：com.echo.config.api.Result<com.echo.modules.ums.dto.res.LoginResDTO>
     * 创建人：@author Echo
     * 创建时间：2023/10/21 18:21
     * version：1.0
     */
    @Override
    public Result<LoginResDTO> login(LoginReqDTO loginReqDTO) {
        LoginResDTO loginResDTO = new LoginResDTO();
        //密码需要客户端加密后传递
        UserDetails userDetails = loadUserByUsername(loginReqDTO.getUsername());
        if (!passwordEncoder.matches(loginReqDTO.getPassword(), userDetails.getPassword())) {
            Asserts.fail("密码不正确");
        }
        if (!userDetails.isEnabled()) {
            Asserts.fail("帐号已被禁用");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);

        if (StrUtil.isBlank(token)) {
            return Result.failed("用户名或密码错误");
        }
        loginResDTO.setToken(token);
        loginResDTO.setTokenHead(tokenHead);
        return Result.success(loginResDTO);
    }


    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsUserServiceImpl
     * 方法名称：refreshToken
     * 方法描述：{ 刷新token }
     * param：[request]
     * return：com.echo.config.api.Result<com.echo.modules.ums.dto.res.RefreshTokenResDTO>
     * 创建人：@author Echo
     * 创建时间：2023/10/21 18:28
     * version：1.0
     */
    @Override
    public Result<RefreshTokenResDTO> refreshToken(HttpServletRequest request) {
        RefreshTokenResDTO refreshTokenResDTO = new RefreshTokenResDTO();
        String oldToken = request.getHeader(tokenHeader);
        String newToken = jwtTokenUtil.refreshHeadToken(oldToken);
        if (StrUtil.isBlank(newToken)) {
            return Result.failed("token已经过期！");
        }
        refreshTokenResDTO.setToken(newToken);
        refreshTokenResDTO.setTokenHead(tokenHead);
        return Result.success(refreshTokenResDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsUser admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsResource> resourceList = getResourceList(admin.getId());
            return new AdminUserDetails(admin, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        List<UmsResource> resourceList = getCacheService().getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = resourceMapper.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            getCacheService().setResourceList(adminId, resourceList);
        }
        return resourceList;
    }

    @Override
    public UmsUser getAdminByUsername(String username) {
        UmsUser admin = getCacheService().getAdmin(username);
        if (admin != null) {
            return admin;
        }
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, username);
        List<UmsUser> adminList = list(wrapper);
        if (adminList != null && adminList.size() > 0) {
            admin = adminList.get(0);
            getCacheService().setAdmin(admin);
            return admin;
        }
        return null;
    }

    @Override
    public UmsUserCacheService getCacheService() {
        return SpringUtil.getBean(UmsUserCacheService.class);
    }

    @Override
    public List<UmsRole> getRoleListByUserId(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public Result<Page<UmsUser>> getPageUserListByKeyword(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsUser> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsUser::getUsername, keyword);
            lambda.or().like(UmsUser::getNickName, keyword);
        }
        return Result.success(page(page, wrapper));
    }

    @Override
    public Result updateUserInfoByUserId(Long id, UmsUser userInfo) {
        userInfo.setId(id);
        UmsUser rawUser = getById(id);
        if (rawUser.getPassword().equals(userInfo.getPassword())) {
            //与原加密密码相同的不需要修改
            userInfo.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(userInfo.getPassword())) {
                userInfo.setPassword(null);
            } else {
                userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            }
        }
        if (updateById(userInfo)) {
            getCacheService().delAdmin(id);
            return Result.success();
        }
        return Result.failed();
    }

    @Override
    public Result updateUserPassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {

        if (StrUtil.isEmpty(updateUserPasswordReqDTO.getUsername())
                || StrUtil.isEmpty(updateUserPasswordReqDTO.getOldPassword())
                || StrUtil.isEmpty(updateUserPasswordReqDTO.getNewPassword())) {
            return Result.failed(VALIDATE_FAILED);
        }
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, updateUserPasswordReqDTO.getUsername());
        List<UmsUser> userList = list(wrapper);
        if (CollUtil.isEmpty(userList)) {
            return Result.failed(THE_USER_IS_NOT_EXIST);
        }
        UmsUser umsUser = userList.get(0);
        if (!passwordEncoder.matches(updateUserPasswordReqDTO.getOldPassword(), umsUser.getPassword())) {
            return Result.failed(THE_OLD_PASSWORD_IS_WRONG);
        }
        umsUser.setPassword(passwordEncoder.encode(updateUserPasswordReqDTO.getNewPassword()));
        if (updateById(umsUser)) {
            getCacheService().delAdmin(umsUser.getId());
            return Result.success();
        }
        return Result.failed(THE_PASSWORD_UPDATE_FAILED);
    }

    @Override
    public Result delUserByUserId(Long id) {
        getCacheService().delAdmin(id);
        removeById(id);
        getCacheService().delResourceList(id);
        return Result.success();
    }

    @Override
    public Result allowUserRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? ZERO : roleIds.size();
        //先删除原来的关系
        QueryWrapper<UmsUserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserRoleRelation::getAdminId, adminId);
        adminRoleRelationService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsUserRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsUserRoleRelation roleRelation = new UmsUserRoleRelation();
                roleRelation.setAdminId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationService.saveBatch(list);
        }
        getCacheService().delResourceList(adminId);
        return Result.success();
    }

}
