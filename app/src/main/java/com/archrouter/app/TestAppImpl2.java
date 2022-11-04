package com.archrouter.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.archrouter.arch.annotation.RouterService;
import com.archrouter.provider.constants.ConstantsService;
import com.archrouter.provider.interfaces.app.ITestApp;

@RouterService(interfaces = ITestApp.class,key= ConstantsService.KEY_TestAppImpl2)
public class TestAppImpl2 implements ITestApp{

    @Override
    public void showInfo(Context context) {
        Toast.makeText(context, "Msg From app TestAppImpl2", Toast.LENGTH_SHORT).show();
    }
}
