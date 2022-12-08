import com.archrouter.plugin.pluginLocal.Dep
buildscript {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven{ setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}

        google()
        maven {
            //本地maven仓库地址
            url = uri("$rootDir/repo")
        }
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.archrouter:pluginRouter:1.0.0")
        classpath("com.archrouter:pluginSwitch:1.0.0")
    }
}

//本地打包注释
subprojects {
    group = Dep.RouterGroup.group
}

plugins {
    id("router-publish") apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

allprojects {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven{ setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}

        google()
        mavenCentral()
        maven {
            //本地maven仓库地址
            url = uri("$rootDir/repo")
        }
//        jcenter() // Warning: this repository is going to shut down soon
    }
    /*tasks.withType(Javadoc) {
        options.addStringOption('Xdoclint:none', '-quiet') //忽略javadoc错误
        options{
            encoding "UTF-8"
            charSet 'UTF-8'
            links "http://docs.oracle.com/javase/7/docs/api"
        }
    }*/

    tasks.register("javadoc",Javadoc::class.java){
        options.apply {
            encoding("UTF-8")
            charset("UTF-8")
            isFailOnError = false
            (this as StandardJavadocDocletOptions).apply {
                addStringOption("Xdoclint:none")
                //links?.add("https://developer.android.google.cn/reference/")
                //links?.add("http://docs.oracle.com/javase/8/docs/api/")
            }
        }
    }
}

/*
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id "com.android.application" version "7.2.0" apply false
    id "com.android.library" version "7.2.0" apply false
    id "org.jetbrains.kotlin.android" version "1.6.10" apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}*/
