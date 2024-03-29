package com.archrouter.router.common;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.archrouter.arch.annotation.RouterPage;
import com.archrouter.router.components.RouterComponents;
import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.core.UriResult;
import com.archrouter.router.utils.LazyInitHelper;
import com.archrouter.router.utils.RouterUtils;

/**
 * 内部页面跳转，由注解 {@link RouterPage} 配置。
 * {@link PageAnnotationHandler} 处理且只处理所有格式为 wm_router://page/* 的URI，根据path匹配，
 * 匹配不到的分发给 {@link NotFoundHandler} ，返回 {@link UriResult#CODE_NOT_FOUND}
 *
 */

public class PageAnnotationHandler extends PathHandler {

    public static final String SCHEME = "wm_router";
    public static final String HOST = "page";
    public static final String SCHEME_HOST = RouterUtils.schemeHost(SCHEME, HOST);

    public static boolean isPageJump(Intent intent) {
        return intent != null && SCHEME_HOST.equals(RouterUtils.schemeHost(intent.getData()));
    }

    private final LazyInitHelper mInitHelper = new LazyInitHelper("PageAnnotationHandler") {
        @Override
        protected void doInit() {
            initAnnotationConfig();
        }
    };

    public PageAnnotationHandler() {
        addInterceptor(NotExportedInterceptor.INSTANCE); // exported全为false
        setDefaultChildHandler(NotFoundHandler.INSTANCE); // 找不到直接终止分发
    }

    /**
     * @see LazyInitHelper#lazyInit()
     */
    public void lazyInit() {
        mInitHelper.lazyInit();
    }

    protected void initAnnotationConfig() {
        RouterComponents.loadAnnotation(this, IPageAnnotationInit.class);
    }

    @Override
    public void handle(@NonNull UriRequest request, @NonNull UriCallback callback) {
        mInitHelper.ensureInit();
        super.handle(request, callback);
    }

    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return SCHEME_HOST.matches(request.schemeHost());
    }

    @Override
    public String toString() {
        return "PageAnnotationHandler";
    }
}