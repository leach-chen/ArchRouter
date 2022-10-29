package com.archrouter.router.common;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriHandler;
import com.archrouter.router.core.UriRequest;

public class WrapperHandler extends UriHandler {

    private final UriHandler mDelegate;

    public WrapperHandler(UriHandler delegate) {
        mDelegate = delegate;
    }

    public UriHandler getDelegate() {
        return mDelegate;
    }

    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        mDelegate.handle(request, callback);
    }

    @Override
    public String toString() {
        return "Delegate(" + mDelegate.toString() + ")";
    }
}
