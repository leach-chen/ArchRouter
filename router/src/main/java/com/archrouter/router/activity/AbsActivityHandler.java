package com.archrouter.router.activity;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.archrouter.router.components.ActivityLauncher;
import com.archrouter.router.components.RouterComponents;
import com.archrouter.router.components.UriSourceTools;
import com.archrouter.router.core.Debugger;
import com.archrouter.router.core.UriCallback;
import com.archrouter.router.core.UriHandler;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.core.UriResult;

/**
 * 跳转Activity的 {@link UriHandler}
 */
public abstract class AbsActivityHandler extends UriHandler {

    @Override
    protected boolean shouldHandle(@NonNull UriRequest request) {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void handleInternal(@NonNull UriRequest request, @NonNull UriCallback callback) {
        // 创建Intent
        Intent intent = createIntent(request);
        if (intent == null || intent.getComponent() == null) {
            Debugger.fatal("AbsActivityHandler.createIntent()应返回的带有ClassName的显式跳转Intent");
            callback.onComplete(UriResult.CODE_ERROR);
            return;
        }
        intent.setData(request.getUri());
        UriSourceTools.setIntentSource(intent, request);
        // 启动Activity
        request.putFieldIfAbsent(ActivityLauncher.FIELD_LIMIT_PACKAGE, limitPackage());
        int resultCode = RouterComponents.startActivity(request, intent);
        // 回调方法
        onActivityStartComplete(request, resultCode);
        // 完成
        if (resultCode != UriResult.CODE_FOR_RESULT) {
            callback.onComplete(resultCode);
        }
    }

    /**
     * 是否只启动当前APP中的Activity
     *
     * @see ActivityLauncher#FIELD_LIMIT_PACKAGE
     */
    protected boolean limitPackage() {
        return true;
    }

    /**
     * 创建用于跳转的Intent，必须是带有ClassName的显式跳转Intent，可覆写添加特殊参数
     */
    @NonNull
    protected abstract Intent createIntent(@NonNull UriRequest request);

    /**
     * 回调方法，子类可在此实现跳转动画等效果
     *
     * @param resultCode 跳转结果
     */
    protected void onActivityStartComplete(@NonNull UriRequest request, int resultCode) {

    }

    @Override
    public String toString() {
        return "ActivityHandler";
    }
}
