package com.archrouter.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.Print
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

        Router.getService(ITestApp::class.java, "111").printLog()
    }


    fun goTestActivity(view:View){
        Router.startUri(this, "/TestActivity")
    }
}