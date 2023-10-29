package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.vo.UmsMenuVO;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.mapper.UmsMenuMapper;
import com.echo.modules.ums.service.UmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.echo.common.constant.CommonConstant.ONE;
import static com.echo.common.constant.CommonConstant.ZERO;
import static com.echo.config.api.ResultCode.*;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    private UmsMenuMapper umsMenuMapper;


    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：addMenu
     * 方法描述：{ 创建菜单 }
     * param：[umsMenu]
     * return：com.echo.config.api.Result<java.lang.Object>
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:17
     * version：1.0
     */
    @Override
    public Result addMenu(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        if (umsMenu.getStatus().equals(ONE)) {
            umsMenu.setStatus(ZERO);
        }
        updateLevel(umsMenu);
        return save(umsMenu) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：updateMenu
     * 方法描述：{ 修改菜单 }
     * param：[id, umsMenu]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:18
     * version：1.0
     */
    @Override
    public Result updateMenu(UmsMenu umsMenu) {
        if (umsMenu.getStatus().equals(ONE)) {
            umsMenu.setStatus(ZERO);
        }
        updateLevel(umsMenu);
        return updateById(umsMenu) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：getMenuById
     * 方法描述：{ 根据ID获取菜单详情 }
     * param：[menuId]
     * return：com.echo.config.api.Result<com.echo.modules.ums.model.UmsMenu>
     * 创建人：@author Echo
     * 创建时间：2023/10/28 11:01
     * version：1.0
     */
    @Override
    public Result<UmsMenu> getMenuById(Long menuId) {
        UmsMenu umsMenu = getOne(new LambdaQueryWrapper<UmsMenu>().eq(UmsMenu::getId, menuId).eq(UmsMenu::getStatus, ZERO));
        if (ObjectUtil.isEmpty(umsMenu)) {
            return Result.failed(THE_MENU_QUERY_FAILED);
        }
        return Result.success(umsMenu);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：delMenu
     * 方法描述：{ 根据ID删除菜单 }
     * param：[menuId]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/28 11:05
     * version：1.0
     */
    @Override
    public Result delMenu(Long menuId) {
        UmsMenu umsMenu = getOne(new LambdaQueryWrapper<UmsMenu>().eq(UmsMenu::getId, menuId).eq(UmsMenu::getStatus, ZERO));
        if (ObjectUtil.isEmpty(umsMenu)) {
            return Result.failed(THE_MENU_QUERY_FAILED);
        }
        umsMenu.setStatus(ONE);
        return updateById(umsMenu) ? Result.success() : Result.failed(THE_MENU_DELETE_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：getPageMenuList
     * 方法描述：{ 分页查询菜单 }
     * param：[parentId, pageSize, pageNum]
     * return：com.echo.config.api.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.echo.modules.ums.model.UmsMenu>>
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:20
     * version：1.0
     */
    @Override
    public Result<Page<UmsMenu>> getPageMenuList(Long parentId, Integer pageSize, Integer pageNum) {
        Page<UmsMenu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsMenu::getParentId, parentId)
                .eq(UmsMenu::getStatus, ZERO)
                .orderByDesc(UmsMenu::getSort);
        return Result.success(page(page, wrapper));
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：getTreesMenuList
     * 方法描述：{ 树形结构返回所有菜单列表 }
     * param：[]
     * return：com.echo.config.api.Result<java.util.List<com.echo.modules.ums.dto.vo.UmsMenuVO>>
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:32
     * version：1.0
     */
    @Override
    public Result<List<UmsMenuVO>> getTreesMenuList() {

        List<UmsMenu> menuList = umsMenuMapper.selectList(new LambdaQueryWrapper<UmsMenu>()
                .eq(UmsMenu::getStatus, ZERO)
        );
        if (CollectionUtil.isEmpty(menuList)) {
            return Result.failed(THE_MENU_QUERY_FAILED);
        }
        List<UmsMenuVO> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return CollectionUtil.isNotEmpty(result) ? Result.success(result) : Result.failed(THE_MENU_QUERY_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：updateMenuHidden
     * 方法描述：{ 修改菜单显示状态 }
     * param：[id, hidden]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:22
     * version：1.0
     */
    @Override
    public Result updateMenuHidden(Long menuId) {
        UmsMenu umsMenu = getOne(new LambdaQueryWrapper<UmsMenu>()
                .eq(UmsMenu::getStatus, ZERO)
                .eq(UmsMenu::getId, menuId)
        );
        if (ObjectUtil.isEmpty(umsMenu)) {
            return Result.failed(THE_MENU_QUERY_FAILED);
        }
        umsMenu.setHidden(ONE);
        return updateById(umsMenu) ? Result.success() : Result.failed(THE_MENU_HIDDEN_FAILED);
    }


    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：updateLevel
     * 方法描述：{ 修改菜单层级 }
     * param：[umsMenu]
     * return：void
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:28
     * version：1.0
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == ZERO) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(ZERO);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = getById(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + ONE);
            } else {
                umsMenu.setLevel(ZERO);
            }
        }
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：covertMenuNode
     * 方法描述：{ UmsMenu转换 }
     * param：[menu, menuList]
     * return：com.echo.modules.ums.dto.vo.UmsMenuVO
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:28
     * version：1.0
     */
    private UmsMenuVO covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuVO node = new UmsMenuVO();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuVO> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
