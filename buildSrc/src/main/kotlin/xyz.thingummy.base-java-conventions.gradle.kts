/*
 * Copyright (c) 2023, Jérôme ROBERT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *   this software and associated documentation files (the “Software”), to deal in the
 *   Software without restriction, including without limitation the rights to use, copy,
 *   modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *   Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *    OTHER DEALINGS IN THE SOFTWARE.
 *
 */

plugins {
    java
    //checkstyle

    // NOTE: external plugin version is specified in implementation dependency artifact of the project's build file
    id("com.github.spotbugs")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.12.0")
    compileOnly("org.projectlombok:lombok:1.18.28")
    compileOnly("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
    /*testCompileOnly("org.projectlombok:lombok:1.18.28")
    testCompileOnly("org.mapstruct:mapstruct:1.5.5.Final")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.28")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")*/
}

tasks.withType<Test> {
    useJUnitPlatform()
}