import com.archrouter.plugin.pluginLocal.Dep
plugins {
    //id("com.android.application")
    id("com.archrouter.plugin.pluginswitch")
    id("com.archrouter.plugin.pluginrouter")
    kotlin("android")
    kotlin("kapt")
}



android {
    compileSdk = Dep.compileSdk

    defaultConfig {
        applicationId = "com.archrouter.app"
        minSdk = Dep.minSdk
        targetSdk = Dep.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")

    implementation(project(":provider"))
    implementation(project(":demoA"))
    implementation(project(":demoB"))
    kapt(project(":apt-arch:arch-compiler"))
    /*implementation project(path: ":apt-arch:arch-annotation")
    annotationProcessor project(path: ":apt-arch:arch-compiler")*/

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}