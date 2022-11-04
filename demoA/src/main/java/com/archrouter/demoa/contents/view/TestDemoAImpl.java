package com.archrouter.demoa.contents.view;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.archrouter.arch.annotation.RouterService;
import com.archrouter.provider.interfaces.demoA.ITestDemoA;

@RouterService(interfaces = ITestDemoA.class,key = "TestDemoA")
public class TestDemoAImpl implements ITestDemoA {
    @Override
    public void showInfo(Context context) {
        Toast.makeText(context, "Msg From demoA TestDemoA", Toast.LENGTH_SHORT).show();
    }
}
