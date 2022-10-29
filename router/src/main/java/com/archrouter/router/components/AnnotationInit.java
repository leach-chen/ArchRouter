package com.archrouter.router.components;

import com.archrouter.router.core.UriHandler;


public interface AnnotationInit<T extends UriHandler> {

    void init(T handler);
}
