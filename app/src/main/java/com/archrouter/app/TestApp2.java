package com.archrouter.app;

import android.util.Log;

import com.archrouter.arch.annotation.RouterService;

@RouterService(interfaces = ITestApp.class,key="222")
public class TestApp2 implements ITestApp{
    @Override
    public void printLog() {
        Log.d("mytest","2222222222222222222222");
    }
}
