package com.archrouter.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.Print
import com.archrouter.provider.constants.ConstantsUri
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
        Router.getService(ITestDemoA::class.java, "TestDemoA")?.showInfo(this)
    }


    fun goFun2(view: View) {
        //startActivity(Intent(this,DemoAActivity::class.java))
        Router.startUri(this, ConstantsUri.PATH_DEMOA)
    }

    fun goFun3(view: View) {
        Router.startUri(this, ConstantsUri.PATH_DEMOB)
    }
}