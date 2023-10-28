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
 * 资源表
 * </p>
 *
 * @author Echo
 * @since 2023-10-21
 */
@Getter
@Setter
@TableName("ums_resource")
@ApiModel(value = "UmsResource对象", description = "资源表")
public class UmsResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("资源URL")
    private String url;

    @ApiModelProperty("资源分类ID")
    private Long categoryId;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("0 有效 1 无效")
    private Integer status;


}
