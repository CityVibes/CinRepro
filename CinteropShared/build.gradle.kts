import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", "1.6.0"))
    }
}

plugins {
    kotlin("multiplatform") version "1.6.10"
    id("maven-publish")
    kotlin("native.cocoapods") version "1.6.10"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    google()
    jcenter()
    mavenCentral()
}

val org.jetbrains.kotlin.konan.target.KonanTarget.archVariant: String
    get() = if (this is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64 || this is org.jetbrains.kotlin.konan.target.KonanTarget.IOS_SIMULATOR_ARM64) {
        "ios-x86_64-simulator"
    } else {
        "ios-arm64"
    }

kotlin {
    fun nativeTargetConfig(): KotlinNativeTarget.() -> Unit = {
        val nativeFrameworkPaths = listOf(
            projectDir.resolve("src/nativeInterop/cinterop/dependencies")
        ).plus(
            listOf(
                "CinteropRepro"
            ).map {
                projectDir.resolve("src/nativeInterop/cinterop/dependencies/$it.xcframework/${konanTarget.archVariant}")
            }
        )
        binaries {
            getTest("DEBUG").apply {
                linkerOpts(nativeFrameworkPaths.map { "-F$it" })
                linkerOpts("-framework CinteropRepro")
                linkerOpts("-ObjC")
            }
        }

        compilations.getByName("main") {
            cinterops.create("CinteropRepro") {
                defFile = projectDir.resolve("src/nativeInterop/cinterop/CinteropRepro.def")
                nativeFrameworkPaths.forEach { includeDirs("$it/CinteropRepro.framework/Headers") }
                compilerOpts(nativeFrameworkPaths.map { "-F$it" })
                extraOpts = listOf("-compiler-option", "-DNS_FORMAT_ARGUMENT(A)=", "-verbose")
            }
        }
    }

    ios(configure = nativeTargetConfig())
    iosSimulatorArm64(configure = nativeTargetConfig())

    sourceSets {
        all {
            languageSettings.apply {
                apiVersion = "1.6"
                languageVersion = "1.6"
                progressiveMode = true
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }

        val commonMain by getting

        val iosSimulatorArm64Main by getting
        val iosMain by getting
        iosSimulatorArm64Main.dependsOn(iosMain)

        val iosSimulatorArm64Test by sourceSets.getting
        val iosTest by sourceSets.getting
        iosSimulatorArm64Test.dependsOn(iosTest)
    }

//    cocoapods {
//
//        framework {
//            summary = "Some description for a Kotlin/Native module"
//            homepage = "Link to a Kotlin/Native module homepage"
//            baseName = "SharedFramework"
////            podfile = project.file("../CinteropReproApp/Podfile")
//
//            isStatic = false
//            transitiveExport = true
//        }
//    }
}