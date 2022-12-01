
plugins {
    id("groovy")
    `java-gradle-plugin`
    `maven-publish`
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    compileOnly("com.android.tools.build:gradle:4.2.1")
    implementation("commons-io:commons-io:2.6")
    implementation("org.javassist:javassist:3.27.0-GA")
}


repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            url = uri("$rootDir/repo")
        }
    }

    publications {
        create<MavenPublication>("release") {
            from(components["java"])
            groupId = "com.archrouter.plugin.pluginRouter"
            artifactId = "Plugin"
            version = "1.0.0"
        }
    }
}