package com.echo.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Getter
@Setter
@TableName("ums_resource_category")
@ApiModel(value = "UmsResourceCategory对象", description = "分类表")
public class UmsResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty("排序")
    private Integer sort;


}
