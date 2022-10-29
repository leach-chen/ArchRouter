package com.archrouter.router.common;

import androidx.annotation.NonNull;

import com.archrouter.router.components.UriSourceTools;
import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriInterceptor;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.core.UriResult;

/**
 * 节点的exported为false，不允许来自外部的跳转，拦截并返回 {@link UriResult#CODE_FORBIDDEN}
 */

public class NotExportedInterceptor implements UriInterceptor {

    public static final NotExportedInterceptor INSTANCE = new NotExportedInterceptor();

    private NotExportedInterceptor() {
    }

    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        if (UriSourceTools.shouldHandle(request, false)) {
            callback.onNext();
        } else {
            callback.onComplete(UriResult.CODE_FORBIDDEN);
        }
    }
}
