package com.archrouter.router.common;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriInterceptor;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.utils.RouterUtils;

import java.util.Map;

/**
 * 给uri添加参数。{@link UriRequest} 可设置 {@link UriParamInterceptor#FIELD_URI_APPEND_PARAMS}, 会被自动添加到uri中。
 */
public class UriParamInterceptor implements UriInterceptor {

    /**
     * {@link UriRequest} 的额外参数，Map&lt;String, String&gt;类型，跳转WebView附加额外参数
     */
    public static final String FIELD_URI_APPEND_PARAMS =
            "com.archrouter.router.UriParamInterceptor.uri_append_params";

    @Override
    public void intercept(@NonNull UriRequest request, @NonNull UriCallback callback) {
        appendParams(request);
        callback.onNext();
    }

    @SuppressWarnings("unchecked")
    protected void appendParams(@NonNull UriRequest request) {
        final Map<String, String> extra = request.getField(
                Map.class, UriParamInterceptor.FIELD_URI_APPEND_PARAMS);
        if (extra != null) {
            Uri uri = RouterUtils.appendParams(request.getUri(), extra);
            request.setUri(uri);
        }
    }
}
