import com.archrouter.plugin.pluginLocal.Dep
plugins {
    id("groovy")
    `java-gradle-plugin`
    `maven-publish`
    signing
    id("router-publish") //本地打包注释
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    compileOnly("com.android.tools.build:gradle:4.2.1")
    implementation("commons-io:commons-io:2.6")
    implementation("org.javassist:javassist:3.27.0-GA")
}

version = Dep.RouterVer.pluginSwitchVer  //本地打包注释

repositories {
    mavenCentral()
}

/*
//本地打包放开
publishing {
    repositories {
        maven {
            url = uri("$rootDir/repo")
        }
    }

    publications {
        create<MavenPublication>("release") {
            groupId = "com.archrouter"
            artifactId = "pluginSwitch"
            version = "1.0.0"
            from(components["java"])
        }
    }
}*/
