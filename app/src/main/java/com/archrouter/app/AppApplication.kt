package com.archrouter.app

import android.app.Application
import com.archrouter.router.Router
import com.archrouter.router.common.DefaultRootUriHandler
import com.archrouter.router.components.DefaultOnCompleteListener

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val rootHandler = DefaultRootUriHandler(this)
        //RouterComponents.setActivityLauncher(ForResultActivityLauncher.Companion.getINSTANCE())
        rootHandler.globalOnCompleteListener = DefaultOnCompleteListener.INSTANCE
        // 初始化
        Router.init(rootHandler)
    }
}