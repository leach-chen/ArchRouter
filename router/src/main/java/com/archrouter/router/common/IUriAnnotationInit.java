package com.archrouter.router.common;

import com.archrouter.arch.annotation.RouterUri;
import com.archrouter.router.components.AnnotationInit;

/**
 * 用于加载 {@link RouterUri} 注解配置的节点。
 * 每个配置了 {@link RouterUri} 注解和注解生成器(annotationProcessor)的Application/Library模块，
 * 都会生成一个此接口的实现类，并在 {@link UriAnnotationHandler} 初始化时被加载。
 *
 */

public interface IUriAnnotationInit extends AnnotationInit<UriAnnotationHandler> {

    @Override
    void init(UriAnnotationHandler handler);
}
