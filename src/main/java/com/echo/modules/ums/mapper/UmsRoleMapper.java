package com.echo.modules.ums.mapper;

import com.echo.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {


    /**
     * 获取用户所有角色
     */
    List<UmsRole> getRoleListByUserId(@Param("userId") Long userId);

}
