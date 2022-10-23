package com.archrouter.router.components;

import androidx.annotation.NonNull;

import com.archrouter.router.service.DefaultFactory;
import com.archrouter.router.service.IFactory;


/**
 * 用于配置组件
 */
public class RouterComponents {


    @NonNull
    private static IFactory sDefaultFactory = DefaultFactory.INSTANCE;



    @NonNull
    public static IFactory getDefaultFactory() {
        return sDefaultFactory;
    }

}
