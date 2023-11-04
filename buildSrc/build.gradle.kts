plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    // Projects should use Maven Central for external dependencies
    mavenCentral()
}

dependencies {
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:5.0.12")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}