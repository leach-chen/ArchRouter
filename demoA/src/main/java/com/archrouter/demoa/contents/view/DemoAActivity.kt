package com.archrouter.demoa.contents.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.archrouter.demoa.R
import com.archrouter.provider.constants.ConstantsService
import com.archrouter.provider.interfaces.demoB.ITestDemoB
import com.archrouter.router.Router

class DemoAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demoa)
    }

    fun goFun1(view: View) {
        Router.getService(ITestDemoB::class.java, ConstantsService.KEY_TestDemoBImpl)
            ?.showInfo(this)
    }
}