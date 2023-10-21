package com.echo.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.model.UmsRole;
import com.echo.modules.ums.mapper.UmsRoleMapper;
import com.echo.modules.ums.model.UmsRoleResourceRelation;
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

import static com.echo.common.constant.CommonConstant.ONE;

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
        role.setStatus(ONE);
        return umsRoleMapper.insert(role) == ONE ? Result.success() : Result.failed();
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
        boolean status = removeByIds(ids);
        if (status) {
            userCacheService.delResourceListByRoleIds(ids);
        }
        return status ? Result.success() : Result.failed();
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
            lambda.like(UmsRole::getRolename, keyword);
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
        //先删除原有关系
        QueryWrapper<UmsRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);
        //批量插入新关系
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

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return null;
    }

}
