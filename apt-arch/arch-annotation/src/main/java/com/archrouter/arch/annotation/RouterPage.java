package com.archrouter.arch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定一个内部页面跳转，此注解可以用在Activity和UriHandler上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterPage {

    /**
     * path
     */
    String[] path();

    /**
     * 要添加的interceptors
     */
    Class[] interceptors() default {};
}
