group = "me.jakejmattson"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.4.10"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("me.jakejmattson:DiscordKt:0.19.1")
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}