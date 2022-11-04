package com.archrouter.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.Print
import com.archrouter.provider.constants.ConstantsUri
import com.archrouter.provider.interfaces.demoA.ITestDemoA
import com.archrouter.router.Router
import com.archrouter.router.common.DefaultUriRequest
import com.archrouter.router.common.PageAnnotationHandler
import com.archrouter.router.components.UriSourceTools
import com.archrouter.router.core.OnCompleteListener
import com.archrouter.router.core.UriRequest
import com.archrouter.router.fragment.v4.FragmentTransactionUriRequest


class MainActivity : AppCompatActivity() {

    @Print
    var name = ""

    @Print
    var age = 0

    lateinit var llContent:LinearLayout;

    lateinit var mContext:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //PrintUtil.`print$$age`()
        llContent = this.findViewById(R.id.llContent)
        this.mContext = this
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
        DefaultUriRequest(this, ConstantsUri.PATH_DEMOB)
            .activityRequestCode(100)
            .from(UriSourceTools.FROM_INTERNAL)
            .putExtra("testValue", "来自主工程的数据")
            .onComplete(object : OnCompleteListener {
                override fun onSuccess(request: UriRequest) {
                }

                override fun onError(request: UriRequest, resultCode: Int) {}
            }) 
            .start()
    }


    fun goFun5(view: View) {
        FragmentTransactionUriRequest(
            this,
            PageAnnotationHandler.SCHEME_HOST + ConstantsUri.PATH_FRAGMENT
        )
            .add(R.id.llContent)
            .putExtra("message","HelloWorld") //测试参数
            .onComplete(object :OnCompleteListener{
                override fun onSuccess(request: UriRequest) {
                    Toast.makeText(mContext, "加载成功", Toast.LENGTH_SHORT).show()
                }

                override fun onError(request: UriRequest, resultCode: Int) {
                }

            })
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(this, data?.getStringExtra("testValue"), Toast.LENGTH_SHORT).show()
    }
}