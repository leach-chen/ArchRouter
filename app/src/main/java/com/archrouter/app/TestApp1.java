package com.archrouter.app;

import android.util.Log;

import com.archrouter.arch.annotation.RouterService;

@RouterService(interfaces = ITestApp.class,key="111")
public class TestApp1 implements ITestApp{
    @Override
    public void printLog() {
        Log.d("mytest","1111111111111111111111");
    }
}
