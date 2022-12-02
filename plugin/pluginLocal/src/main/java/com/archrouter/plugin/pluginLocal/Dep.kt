package com.archrouter.plugin.pluginLocal

import org.gradle.api.JavaVersion

object Dep {

    val javaVersion = JavaVersion.VERSION_11
    const val kotlinJvmTarget = "11"
    const val compileSdk = 31
    const val minSdk = 21
    const val targetSdk = 31


    object RouterVer {
        const val routerVer = "1.0.0-SNAPSHOT"
        const val archVer = "1.0.0-SNAPSHOT"
        const val pluginSwitchVer = "1.0.0-SNAPSHOT"
        const val pluginRouterVer = "1.0.0-SNAPSHOT"
    }

    object RouterGroup {
        const val group = "com.archrouter"
    }
}