package com.echo.modules.ums.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.echo.config.api.Result;
import com.echo.modules.ums.dto.vo.UmsMenuVO;
import com.echo.modules.ums.model.UmsMenu;
import com.echo.modules.ums.mapper.UmsMenuMapper;
import com.echo.modules.ums.service.UmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.echo.common.constant.CommonConstant.ONE;
import static com.echo.common.constant.CommonConstant.ZERO;
import static com.echo.config.api.ResultCode.THE_MENU_QUERY_FAILED;

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

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：create
     * 方法描述：{ 创建菜单 }
     * param：[umsMenu]
     * return：com.echo.config.api.Result<java.lang.Object>
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:17
     * version：1.0
     */
    @Override
    public Result create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return save(umsMenu) ? Result.success() : Result.failed();
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：update
     * 方法描述：{ 修改菜单 }
     * param：[id, umsMenu]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:18
     * version：1.0
     */
    @Override
    public Result update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return updateById(umsMenu) ? Result.success() : Result.failed();
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
        List<UmsMenu> menuList = list();
        List<UmsMenuVO> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return CollectionUtil.isNotEmpty(result) ? Result.success(result) : Result.failed(THE_MENU_QUERY_FAILED);
    }

    /**
     * 类路径：com.echo.modules.ums.service.impl
     * 类名称：UmsMenuServiceImpl
     * 方法名称：updateHidden
     * 方法描述：{ 修改菜单显示状态 }
     * param：[id, hidden]
     * return：com.echo.config.api.Result
     * 创建人：@author Echo
     * 创建时间：2023/10/23 20:22
     * version：1.0
     */
    @Override
    public Result updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return updateById(umsMenu) ? Result.success() : Result.failed();
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
