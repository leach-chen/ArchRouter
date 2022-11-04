package com.archrouter.demoa.contents.view;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.archrouter.arch.annotation.RouterUri;
import com.archrouter.provider.constants.ConstantsUri;
import com.archrouter.router.activity.AbsActivityHandler;
import com.archrouter.router.core.UriRequest;


@RouterUri(path = ConstantsUri.PATH_DEMOA, interceptors = DemoAInterceptors.class)
public class DemoAHandler extends AbsActivityHandler {
    @NonNull
    @Override
    protected Intent createIntent(@NonNull UriRequest request) {
        return new Intent(request.getContext(), DemoAActivity.class);
    }
}
