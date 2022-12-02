import org.gradle.internal.jvm.Jvm
import com.archrouter.plugin.pluginLocal.Dep

plugins {
    id("java")
    `maven-publish`
    signing
    id("router-publish")
}

version = Dep.RouterVer.archVer

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    annotationProcessor("com.google.auto.service:auto-service:1.0-rc7")
    implementation("com.google.auto.service:auto-service:1.0-rc7")
    implementation("com.squareup:javapoet:1.13.0")
    //compileOnly files(Jvm.current().getToolsJar())
    implementation(project(":apt-arch:arch-annotation"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}