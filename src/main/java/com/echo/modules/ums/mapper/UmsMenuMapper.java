package com.echo.modules.ums.mapper;

import com.echo.modules.ums.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据管理员ID获取对应菜单
     */
    List<UmsMenu> getMenuListByUserId(@Param("userId") Long userId);


    /**
     * 根据角色ID获取菜单
     */
    List<UmsMenu> getMenusByRoleId(@Param("roleId") Long roleId);

}
