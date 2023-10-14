package com.echo.config.annos;


import java.lang.annotation.*;

/****************************************************
 * 创建人：Echo
 * 创建时间: 2023/10/14 22:42
 * 项目名称: {cli}
 * 文件名称: WebLog
 * 文件描述: [切面日志注解]
 * version：1.0
 * All rights Reserved, Designed By Echo
 *
 ********************************************************/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLogAnno {

    /**
     * 日志描述信息
     * @return
     */
    String description() default "";

}
