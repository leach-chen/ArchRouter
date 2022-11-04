package com.archrouter.router.fragment.v4;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.archrouter.router.core.Debugger;
import com.archrouter.router.core.UriRequest;
import com.archrouter.router.fragment.AbsFragmentUriRequest;
import com.archrouter.router.fragment.FragmentTransactionHandler;
import com.archrouter.router.fragment.StartFragmentAction;


/**
 * 通过Uri 创建 v4 Fragment的Request
 * 通过 FragmentBuildUriRequest.FRAGMENT 获取返回的Fragment
 */
public class FragmentBuildUriRequest extends AbsFragmentUriRequest {
    public final static String FRAGMENT = "CUSTOM_FRAGMENT_OBJ";

    public FragmentBuildUriRequest(@NonNull Context context, String uri) {
        super(context, uri);
    }

    @Override
    protected StartFragmentAction getStartFragmentAction() {
        return new StartFragmentAction() {
            @Override
            public boolean startFragment(@NonNull UriRequest request, @NonNull Bundle bundle) throws ActivityNotFoundException, SecurityException {
                String fragmentClassName = request.getStringField(FragmentTransactionHandler.FRAGMENT_CLASS_NAME);
                if (TextUtils.isEmpty(fragmentClassName)) {
                    Debugger.fatal("FragmentBuildUriRequest.handleInternal()应返回的带有ClassName");
                    return false;
                }
                try {
                    Fragment fragment = Fragment.instantiate(request.getContext(), fragmentClassName, bundle);
                    if (fragment == null) {
                        return false;
                    }
                    //自定义处理不做transaction，直接放在request里面回调
                    request.putField(FRAGMENT,fragment);
                    return true;
                } catch (Exception e) {
                    Debugger.e(e);
                    return false;
                }
            }
        };
    }
}
