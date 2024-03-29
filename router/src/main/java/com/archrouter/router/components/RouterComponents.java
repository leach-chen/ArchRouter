package com.archrouter.router.components;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriHandler;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.service.DefaultFactory;
import com.archrouter.router.service.IFactory;

/**
 * 用于配置组件
 *
 */
public class RouterComponents {

    @NonNull
    private static AnnotationLoader sAnnotationLoader = DefaultAnnotationLoader.INSTANCE;

    @NonNull
    private static ActivityLauncher sActivityLauncher = DefaultActivityLauncher.INSTANCE;

    @NonNull
    private static IFactory sDefaultFactory = DefaultFactory.INSTANCE;

    public static void setAnnotationLoader(AnnotationLoader loader) {
        sAnnotationLoader = loader == null ? DefaultAnnotationLoader.INSTANCE : loader;
    }

    public static void setActivityLauncher(ActivityLauncher launcher) {
        sActivityLauncher = launcher == null ? DefaultActivityLauncher.INSTANCE : launcher;
    }

    public static void setDefaultFactory(IFactory factory) {
        sDefaultFactory = factory == null ? DefaultFactory.INSTANCE : factory;
    }

    @NonNull
    public static IFactory getDefaultFactory() {
        return sDefaultFactory;
    }

    /**
     * @see AnnotationLoader#load(UriHandler, Class)
     */
    public static <T extends UriHandler> void loadAnnotation(T handler, Class<? extends AnnotationInit<T>> initClass) {
        sAnnotationLoader.load(handler, initClass);
    }

    /**
     * @see ActivityLauncher#startActivity(UriRequest, Intent)
     */
    public static int startActivity(@NonNull UriRequest request, @NonNull Intent intent) {
        return sActivityLauncher.startActivity(request, intent);
    }
}
