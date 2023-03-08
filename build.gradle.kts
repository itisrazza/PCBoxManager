plugins {
    application
    java
    kotlin("jvm") version "1.8.0"
}

group = "io.razza.pcboxmanager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.formdev:flatlaf:3.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

//tasks.register<Jar>("uberJar") {
//    archiveClassifier.set("uber")
//
//    from(sourceSets.main.get().output)
//
//    dependsOn(configurations.runtimeClasspath)
//    from({
//        configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) }
//    })
//}

application {
    mainClass.set("io.razza.pcboxmanager.ApplicationKt")
}

kotlin {
    jvmToolchain(11)
}