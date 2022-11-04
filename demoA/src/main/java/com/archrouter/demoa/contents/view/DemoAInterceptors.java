package com.archrouter.demoa.contents.view;

import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriInterceptor;
import com.archrouter.router.core.UriRequest;


public class DemoAInterceptors implements UriInterceptor {

    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        //callback.onComplete();
        Toast.makeText(request.getContext(), "拦截到跳转请求，1秒后进行跳转", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onNext();
            }
        }, 1000);
    }
}
