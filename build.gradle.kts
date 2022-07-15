import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.github.RecraftedCivilizations"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/public/")
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:+")
    implementation("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    implementation("org.bukkit:bukkit:1.19-R0.1-SNAPSHO")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}