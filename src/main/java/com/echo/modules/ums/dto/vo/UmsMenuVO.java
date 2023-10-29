package com.echo.modules.ums.dto.vo;

import com.echo.modules.ums.model.UmsMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmsMenuVO extends UmsMenu {

    /**
     * 子级菜单
     */
    private List<UmsMenuVO> children;

}
