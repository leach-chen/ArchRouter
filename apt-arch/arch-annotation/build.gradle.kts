import com.archrouter.plugin.pluginLocal.Dep

plugins{
    id("java")
    `maven-publish`
    signing
    id("router-publish")
}

version = Dep.RouterVer.archVer

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}