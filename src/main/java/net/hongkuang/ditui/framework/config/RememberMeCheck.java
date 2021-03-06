package net.hongkuang.ditui.framework.config;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015/11/25.
 * 自定义注解SystemControllerCheck
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RememberMeCheck {
    String description() default "";
}
