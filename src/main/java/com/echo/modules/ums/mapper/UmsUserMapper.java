package com.echo.modules.ums.mapper;

import com.echo.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
public interface UmsUserMapper extends BaseMapper<UmsUser> {

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);

}
