package com.archrouter.router.core;

import androidx.annotation.NonNull;

/**
 * URI分发完成的监听器
 */
public interface OnCompleteListener extends UriResult {

    /**
     * 分发成功
     */
    void onSuccess(@NonNull UriRequest request);

    /**
     * 分发失败
     *
     * @param resultCode 错误代码，可参考 {@link UriResult}
     */
    void onError(@NonNull UriRequest request, int resultCode);
}
