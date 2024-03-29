package com.archrouter.router.service;

import androidx.annotation.NonNull;

/**
 * 从Class构造实例
 */
public interface IFactory {

    @NonNull
    <T> T create(@NonNull Class<T> clazz) throws Exception;
}
