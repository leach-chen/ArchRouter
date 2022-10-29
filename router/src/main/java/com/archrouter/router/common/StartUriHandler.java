package com.archrouter.router.common;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.archrouter.router.components.ActivityLauncher;
import com.archrouter.router.components.RouterComponents;
import com.archrouter.router.components.UriSourceTools;
import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriHandler;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.core.UriResult;

/**
 * 尝试直接用 {@link Intent#setData(Uri)} 隐式跳转启动Uri的Handler
 */
public class StartUriHandler extends UriHandler {

    /**
     * 是否使用 {@link StartUriHandler} 尝试通过Uri隐式跳转，默认为true
     */
    public static final String FIELD_TRY_START_URI =
            "com.archrouter.router.common.try_start_uri";

    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return request.getBooleanField(FIELD_TRY_START_URI, true);
    }

    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(request.getUri());
        UriSourceTools.setIntentSource(intent, request);
        request.putFieldIfAbsent(ActivityLauncher.FIELD_LIMIT_PACKAGE, limitPackage());
        int resultCode = RouterComponents.startActivity(request, intent);
        handleResult(callback, resultCode);
    }

    /**
     * 是否只启动当前APP中的Activity
     *
     * @see ActivityLauncher#FIELD_LIMIT_PACKAGE
     */
    protected boolean limitPackage() {
        return false;
    }

    /**
     * 跳转Activity后的行为，可以继承覆盖。
     * 默认行为：跳转成功后结束分发，跳转失败后继续分发给其他Handler。
     */
    protected void handleResult(@NonNull UriCallback callback, int resultCode) {
        if (resultCode == UriResult.CODE_SUCCESS) {
            callback.onComplete(resultCode);
        } else if (resultCode == UriResult.CODE_FOR_RESULT) {
            // 特殊化处理这个code 特意不终止这套责任链  让回调可以被for result处理
        } else {
            callback.onNext();
        }
    }

    @Override
    public String toString() {
        return "StartUriHandler";
    }
}
