plugins {
    java
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.itskool"
version = "1.0"

repositories {
    mavenCentral()
}

val vertxVersion by extra("4.0.0")
val logbackClassicVersion by extra("1.2.3")
val mpromVersion by extra("1.6.3")

dependencies {
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-micrometer-metrics:$vertxVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$mpromVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.itskool.Main"
    }
}
