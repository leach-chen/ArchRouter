package com.archrouter.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.Print
import com.archrouter.provider.constants.ConstantsUri
import com.archrouter.provider.interfaces.demoA.ITestDemoA
import com.archrouter.router.Router
import com.archrouter.router.common.DefaultUriRequest
import com.archrouter.router.components.UriSourceTools
import com.archrouter.router.core.OnCompleteListener
import com.archrouter.router.core.UriRequest


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

    fun goFun4(view: View) {
        //Router.startUri(this, ConstantsUri.PATH_DEMOB)
        DefaultUriRequest(this, ConstantsUri.PATH_DEMOB) //传入context和目标uri
            // startActivityForResult使用的RequestCode
            .activityRequestCode(100) // 设置跳转来源，默认为内部跳转，还可以是来自WebView、来自Push通知等。
            // 目标Activity可通过UriSourceTools区分跳转来源。
            .from(UriSourceTools.FROM_INTERNAL) // Intent加参数
            .putExtra("testValue", "来自主工程的数据")
            .onComplete(object : OnCompleteListener {
                override fun onSuccess(request: UriRequest) {
                    //ToastUtils.showToast(request.context, "跳转成功")
                }

                override fun onError(request: UriRequest, resultCode: Int) {}
            }) // 这里的start实际也是调用了Router.startUri方法
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, data?.getStringExtra("testValue"), Toast.LENGTH_SHORT).show()
    }
}