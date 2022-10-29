package com.archrouter.router.common;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriHandler;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.core.UriResult;

/**
 * 不支持的跳转链接，返回 {@link UriResult#CODE_NOT_FOUND}
 */

public class NotFoundHandler extends UriHandler {

    public static final NotFoundHandler INSTANCE = new NotFoundHandler();

    @Override
    public boolean shouldHandle(@NonNull UriRequest request) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        callback.onComplete(UriResult.CODE_NOT_FOUND);
    }

    @Override
    public String toString() {
        return "NotFoundHandler";
    }
}
