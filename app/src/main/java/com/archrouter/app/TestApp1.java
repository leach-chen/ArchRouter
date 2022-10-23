package com.archrouter.app;

import com.archrouter.arch.annotation.RouterService;

@RouterService(interfaces = TestApp1.class,key="111")
public class TestApp1 implements ITestApp{
}
