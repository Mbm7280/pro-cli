package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.mapper.UmsMenuMapper;
import com.echo.modules.ums.mapper.UmsResourceMapper;
import com.echo.modules.ums.model.*;
import com.echo.modules.ums.mapper.UmsRoleMapper;
import com.echo.modules.ums.service.UmsRoleMenuRelationService;
import com.echo.modules.ums.service.UmsRoleResourceRelationService;
import com.echo.modules.ums.service.UmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echo.modules.ums.service.UmsUserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.echo.common.constant.CommonConstant.ONE;
import static com.echo.common.constant.CommonConstant.ZERO;
import static com.echo.config.api.ResultCode.*;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Autowired
    private UmsMenuMapper menuMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private UmsUserCacheService userCacheService;

    @Autowired
    private UmsRoleMenuRelationService roleMenuRelationService;
    @Autowired
    private UmsRoleResourceRelationService roleResourceRelationService;

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：addRole
     * 方法描述：{ 添加角色 }
     * param：[role]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/21 20:10
     * version：1.0
     */
    @Override
    public Result addRole(UmsRole role) {
        role.setCreateTime(new Date());
        if (role.getStatus().equals(ZERO)) {
            role.setStatus(ONE);
        }
        return umsRoleMapper.insert(role) == ONE ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：updateRoleByRoleId
     * 方法描述：{ 修改角色 }
     * param：[role]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/26 19:12
     * version：1.0
     */
    @Override
    public Result updateRoleByRoleId(UmsRole role) {
        UmsRole umsRole = getById(role.getId());
        if (ObjectUtil.isEmpty(umsRole)) {
            return Result.failed(THE_ROLE_UPDATE_FAILED);
        }
        if (role.getStatus().equals(ZERO)) {
            role.setStatus(ONE);
        }
        return updateById(role) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：getAllRoles
     * 方法描述：{ 获取所有角色 }
     * param：[]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.model.UmsRole>>
     * 创建人：@author Echo
     * 创建时间：2023/10/26 19:21
     * version：1.0
     */
    @Override
    public Result<List<UmsRole>> getAllRoles() {
        List<UmsRole> umsRoleList = list();
        return CollUtil.isNotEmpty(umsRoleList) ? Result.success(umsRoleList) : Result.failed(THE_ROLE_QUERY_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：delRoleBatch
     * 方法描述：{ 批量删除角色 }
     * param：[ids]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/21 20:20
     * version：1.0
     */
    @Override
    public Result delRoleBatch(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Result.failed(VALIDATE_FAILED);
        }
        List<UmsRole> umsRoleList = umsRoleMapper.selectList(new LambdaQueryWrapper<UmsRole>().eq(UmsRole::getStatus, ONE).in(UmsRole::getId, ids));
        if (CollUtil.isEmpty(umsRoleList)) {
            return Result.failed(THE_ROLE_QUERY_FAILED);
        }
        List<UmsRole> realDelRoleList = null;
        if (umsRoleList.size() != ids.size()) {
            realDelRoleList = umsRoleList.stream().filter(role -> ids.contains(role.getId())).collect(Collectors.toList());
        }
        if (CollUtil.isEmpty(realDelRoleList)) {
            return Result.failed(VALIDATE_FAILED);
        }
        realDelRoleList.stream().forEach(role -> role.setStatus(ZERO));
        boolean result = updateBatchById(realDelRoleList);

        List<Long> roleIds = new ArrayList<>();
        realDelRoleList.forEach(role -> roleIds.add(role.getId()));

        if (result) {
            userCacheService.delResourceListByRoleIds(roleIds);
        }

        return result ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：getPageRoleList
     * 方法描述：{ 分页获取角色列表 }
     * param：[keyword, pageSize, pageNum]
     * return：com.echo.config.api.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.echo.modules.ums.model.UmsRole>>
     * 创建人：@author Echo
     * 创建时间：2023/10/21 20:24
     * version：1.0
     */
    @Override
    public Result<Page<UmsRole>> getPageRoleList(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsRole::getRoleName, keyword);
        }
        return Result.success(page(page, wrapper));
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：allocResource
     * 方法描述：{ 给角色分配资源 }
     * param：[roleId, resourceIds]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/21 20:28
     * version：1.0
     */
    @Override
    public Result allocResource(Long roleId, List<Long> resourceIds) {
        UmsRole umsRole = this.getOne(new LambdaQueryWrapper<UmsRole>().eq(UmsRole::getStatus, ONE).eq(UmsRole::getId, roleId));
        if (ObjectUtil.isEmpty(umsRole)) {
            return Result.failed(THE_ROLE_QUERY_FAILED);
        }
        // 先删除原有关系
        QueryWrapper<UmsRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);
        // 批量插入新关系
        List<UmsRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        userCacheService.delResourceListByRole(roleId);
        return Result.success();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：allocMenu
     * 方法描述：{ 给角色分配菜单 }
     * param：[roleId, menuIds]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/24 19:40
     * version：1.0
     */
    @Override
    public Result allocMenu(Long roleId, List<Long> menuIds) {
        UmsRole umsRole = this.getOne(new LambdaQueryWrapper<UmsRole>().eq(UmsRole::getStatus, ONE).eq(UmsRole::getId, roleId));
        if (ObjectUtil.isEmpty(umsRole)) {
            return Result.failed(THE_ROLE_QUERY_FAILED);
        }
        //先删除原有关系
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuRelation::getRoleId, roleId);
        roleMenuRelationService.remove(wrapper);
        //批量插入新关系
        List<UmsRoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size() > ZERO ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：delRoleByRoleId
     * 方法描述：{ 删除角色 }
     * param：[id]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/26 20:21
     * version：1.0
     */
    @Override
    public Result delRoleByRoleId(Long id) {
        UmsRole umsRole = getById(id);
        if (ObjectUtil.isEmpty(umsRole)) {
            return Result.failed(THE_ROLE_QUERY_FAILED);
        }
        umsRole.setStatus(ZERO);
        return updateById(umsRole) ? Result.success() : Result.failed(THE_ROLE_DELETE_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：getMenuListByAdminId
     * 方法描述：{ 根据管理员ID获取对应菜单 }
     * param：[adminId]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.model.UmsMenu>>
     * 创建人：@author Echo
     * 创建时间：2023/10/22 16:55
     * version：1.0
     */
    @Override
    public List<UmsMenu> getMenuListByUserId(Long userId) {
        List<UmsMenu> menuList = menuMapper.getMenuListByUserId(userId);
        return menuList;
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：getMenusByRoleId
     * 方法描述：{ 获取角色相关菜单 }
     * param：[roleId]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.model.UmsMenu>>
     * 创建人：@author Echo
     * 创建时间：2023/10/22 16:51
     * version：1.0
     */
    @Override
    public Result<List<UmsMenu>> getMenusByRoleId(Long roleId) {
        List<UmsMenu> menuList = menuMapper.getMenusByRoleId(roleId);
        return CollUtil.isEmpty(menuList) ? Result.failed(THE_MENU_QUERY_FAILED) : Result.success(menuList);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsRoleServiceImpl
     * 方法名称：getResourcesByRoleId
     * 方法描述：{ 获取角色相关资源 }
     * param：[roleId]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.model.UmsResource>>
     * 创建人：@author Echo
     * 创建时间：2023/10/22 16:46
     * version：1.0
     */
    @Override
    public Result<List<UmsResource>> getResourcesByRoleId(Long roleId) {
        List<UmsResource> resourceList = resourceMapper.getResourcesByRoleId(roleId);
        return CollUtil.isEmpty(resourceList) ? Result.failed(THE_RESOURCE_QUERY_FAILED) : Result.success(resourceList);
    }

}
