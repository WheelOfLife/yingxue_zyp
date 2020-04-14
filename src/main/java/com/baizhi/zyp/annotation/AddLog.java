package com.baizhi.zyp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
@Target({ElementType.METHOD})//在方法上
@Retention(RetentionPolicy.RUNTIME)//在运行时生效
public @interface AddLog {
    String name() default "";
}
