package com.archrouter.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.Print
import com.archrouter.demoa.contents.view.DemoAActivity
import com.archrouter.provider.interfaces.demoA.ITestDemoA
import com.archrouter.router.Router

class MainActivity : AppCompatActivity() {

    @Print
    var name = ""

    @Print
    var age = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //PrintUtil.`print$$age`()
    }


    fun goFun1(view:View){
        //Router.startUri(this, "/TestActivity")
        Router.getService(ITestDemoA::class.java, "TestDemoA")?.showInfo(this)
        startActivity(Intent(this,DemoAActivity::class.java))
    }
}