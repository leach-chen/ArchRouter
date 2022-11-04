package com.archrouter.demob.view;

import android.content.Context;
import android.widget.Toast;

import com.archrouter.arch.annotation.RouterService;
import com.archrouter.provider.constants.ConstantsService;
import com.archrouter.provider.interfaces.demoB.ITestDemoB;

@RouterService(interfaces = ITestDemoB.class, key = ConstantsService.KEY_TestDemoBImpl)
public class TestDemoBImpl implements ITestDemoB {
    @Override
    public void showInfo(Context context) {
        Toast.makeText(context, "Msg From demoB TestDemoB", Toast.LENGTH_SHORT).show();
    }
}
