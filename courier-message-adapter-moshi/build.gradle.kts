import plugin.KotlinLibraryConfigurationPlugin

apply<KotlinLibraryConfigurationPlugin>()
apply("$rootDir/gradle/publish-artifact-task-java.gradle")
apply("$rootDir/gradle/script-ext.gradle")

val version = ext.get("gitVersionName")

ext {
    set("name", "courier-message-adapter-moshi")
    set("publish", true)
    set("version", ext.get("gitVersionName"))
    set("minimumCoverage", "0.8")
}

plugins {
    id("java-library")
    kotlin("jvm")
    id(ScriptPlugins.apiValidator) version versions.apiValidator
}

dependencies {
    if (project.ext.get("isCI") as Boolean) {
        api("com.gojek.android:courier-core:$version")
    } else {
        api(project(":courier-core"))
    }

    implementation(deps.kotlin.stdlib.core)
    api(deps.square.moshi)

    testImplementation(deps.android.test.kotlinTestJunit)
}