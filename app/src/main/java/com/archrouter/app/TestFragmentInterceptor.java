package com.archrouter.app;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriInterceptor;
import com.archrouter.router.core.UriRequest;

public class TestFragmentInterceptor implements UriInterceptor {
    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        //callback.onComplete(123);
        callback.onNext();
    }
}
