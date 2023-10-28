package com.echo.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.vo.UmsMenuVO;
import com.echo.modules.ums.model.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 创建菜单
     */
    Result addMenu(UmsMenu umsMenu);

    /**
     * 修改菜单
     */
    Result updateMenu(UmsMenu umsMenu);

    /**
     * 根据ID获取菜单详情
     *
     * @param menuId
     * @return
     */
    Result<UmsMenu> getMenuById(Long menuId);


    /**
     * 根据ID删除菜单
     *
     * @param menuId
     * @return
     */
    Result delMenu(Long menuId);

    /**
     * 分页查询菜单
     */
    Result<Page<UmsMenu>> getPageMenuList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    Result<List<UmsMenuVO>> getTreesMenuList();

    /**
     * 修改菜单显示状态
     */
    Result updateMenuHidden(Long menuId);


}
