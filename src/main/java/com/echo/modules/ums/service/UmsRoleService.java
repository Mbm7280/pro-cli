package com.echo.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.model.UmsResource;
import com.echo.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     */
    Result addRole(UmsRole role);


    /**
     * 批量删除角色
     */
    Result delRoleBatch(List<Long> ids);


    /**
     * 分页获取角色列表
     */
    Result<Page<UmsRole>> getPageRoleList(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    Result<List<UmsMenu>> getMenuListByAdminId(Long adminId);

    /**
     * 获取角色相关菜单
     */
    Result<List<UmsMenu>> getMenusByRoleId(Long roleId);

    /**
     * 获取角色相关资源
     */
    Result<List<UmsResource>> getResourcesByRoleId(Long roleId);

    /**
     * 给角色分配资源
     */
    @Transactional
    Result allocResource(Long roleId, List<Long> resourceIds);


    /**
     * 修改角色状态
     *
     * @param id
     * @param status
     * @return
     */
    Result updateRoleStatus(Long id, Integer status);

}
