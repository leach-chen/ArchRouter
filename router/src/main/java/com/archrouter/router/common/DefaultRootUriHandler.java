package com.archrouter.router.common;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.archrouter.arch.annotation.RouterUri;
import com.archrouter.router.components.DefaultOnCompleteListener;
import com.archrouter.router.core.RootUriHandler;
import com.archrouter.router.regex.RegexAnnotationHandler;
import com.archrouter.router.utils.LazyInitHelper;

/**
 * 默认的RootHandler实现
 */

public class DefaultRootUriHandler extends RootUriHandler {

    private final PageAnnotationHandler mPageAnnotationHandler;
    private final UriAnnotationHandler mUriAnnotationHandler;
    private final RegexAnnotationHandler mRegexAnnotationHandler;

    public DefaultRootUriHandler(Context context) {
        this(context, null, null);
    }

    /**
     * @param defaultScheme {@link RouterUri} 没有指定scheme时，则使用这里设置的defaultScheme
     * @param defaultHost   {@link RouterUri} 没有指定host时，则使用这里设置的defaultHost
     */
    public DefaultRootUriHandler(Context context,
                                 @Nullable String defaultScheme, @Nullable String defaultHost) {
        super(context);
        mPageAnnotationHandler = createPageAnnotationHandler();
        mUriAnnotationHandler = createUriAnnotationHandler(defaultScheme, defaultHost);
        mRegexAnnotationHandler = createRegexAnnotationHandler();

        // 按优先级排序，数字越大越先执行

        // 处理RouterPage注解定义的内部页面跳转，如果注解没定义，直接结束分发
        addChildHandler(mPageAnnotationHandler, 300);
        // 处理RouterUri注解定义的URI跳转，如果注解没定义，继续分发到后面的Handler
        addChildHandler(mUriAnnotationHandler, 200);
        // 处理RouterRegex注解定义的正则匹配
        addChildHandler(mRegexAnnotationHandler, 100);
        // 添加其他用户自定义Handler...

        // 都没有处理，则尝试使用默认的StartUriHandler直接启动Uri
        addChildHandler(new StartUriHandler(), -100);
        // 全局OnCompleteListener，用于输出跳转失败提示信息
        setGlobalOnCompleteListener(DefaultOnCompleteListener.INSTANCE);
    }

    /**
     * @see LazyInitHelper#lazyInit()
     */
    public void lazyInit() {
        mPageAnnotationHandler.lazyInit();
        mUriAnnotationHandler.lazyInit();
        mRegexAnnotationHandler.lazyInit();
    }

    public PageAnnotationHandler getPageAnnotationHandler() {
        return mPageAnnotationHandler;
    }

    public UriAnnotationHandler getUriAnnotationHandler() {
        return mUriAnnotationHandler;
    }

    public RegexAnnotationHandler getRegexAnnotationHandler() {
        return mRegexAnnotationHandler;
    }

    @NonNull
    protected PageAnnotationHandler createPageAnnotationHandler() {
        return new PageAnnotationHandler();
    }

    @NonNull
    protected UriAnnotationHandler createUriAnnotationHandler(@Nullable String defaultScheme,
                                                              @Nullable String defaultHost) {
        return new UriAnnotationHandler(defaultScheme, defaultHost);
    }

    @NonNull
    protected RegexAnnotationHandler createRegexAnnotationHandler() {
        return new RegexAnnotationHandler();
    }
}
