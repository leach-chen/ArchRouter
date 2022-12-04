import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    idea
    kotlin("jvm") version "1.6.20"
    `kotlin-dsl`
}

buildscript {
    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        maven{ setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}

        mavenCentral()
        google()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    }
}

group = "com.archrouter.plugin.pluginLocal"
version = "0.0.1"


gradlePlugin {
    plugins {
        create("router-publish"){
            id = "router-publish"
            implementationClass = "com.archrouter.plugin.pluginLocal.PublishPlugin"
        }
    }
}

repositories {
    maven { setUrl("https://maven.aliyun.com/repository/public/") }
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    maven{ setUrl("https://maven.aliyun.com/nexus/content/groups/public/")}
    
    mavenCentral()
    google()
}

dependencies {
    api(gradleApi())
    api(gradleKotlinDsl())
    api(kotlin("gradle-plugin", version = "1.6.20"))
    api(kotlin("gradle-plugin-api", version = "1.6.20"))
    api("com.android.tools.build:gradle-api:7.1.3")
    api("com.android.tools.build:gradle:7.1.3")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}