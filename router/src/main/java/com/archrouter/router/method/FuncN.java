package com.archrouter.router.method;

public interface FuncN<R> extends Function {
    R call(Object... args);
}
