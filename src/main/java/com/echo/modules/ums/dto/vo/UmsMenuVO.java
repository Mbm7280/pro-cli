package com.echo.modules.ums.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsMenuVO {

    /**
     * 子级菜单
     */
    private List<UmsMenuVO> children;

}
