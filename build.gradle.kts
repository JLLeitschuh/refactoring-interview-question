import org.gradle.kotlin.dsl.apply
import org.junit.platform.console.options.Details

plugins {
    id("com.diffplug.gradle.spotless") version "3.6.0"
    id("org.junit.platform.gradle.plugin") version "1.1.0-M1" apply false
    kotlin("jvm") version "1.1.60"
}

object Versions {
    const val KTLINT = "0.9.2"
    const val KOTLIN = "1.1.60"
}

allprojects {
    apply {
        plugin("com.diffplug.gradle.spotless")
    }

    repositories {
        mavenCentral()
    }

    spotless {
        kotlinGradle {
            ktlint(Versions.KTLINT)
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

subprojects {
    apply {
        plugin("java-library")
        plugin("kotlin")
        plugin("org.junit.platform.gradle.plugin")
    }

    dependencies {

        "api"(kotlin("stdlib", Versions.KOTLIN))
        "api"(kotlin("reflect", Versions.KOTLIN))
        "api"(group = "com.google.guava", name = "guava", version = "22.0")

        /*
         * Gives you various options of assertion library you would like to use.
         * You don't have to use either of these if you don't want to.
         */
        "testImplementation"(group = "com.natpryce", name = "hamkrest", version = "1.4.1.0")
        "testImplementation"(group = "org.hamcrest", name = "hamcrest-all", version = "1.3")
        "testImplementation"(group = "it.ozimov", name = "guava-hamcrest-matchers", version = "1.3.0")

        fun junitJupiter(name: String) = create(group = "org.junit.jupiter", name = name, version = "5.1.0-M1")
        "testImplementation"(junitJupiter("junit-jupiter-api"))
        "testImplementation"(junitJupiter("junit-jupiter-params"))
        "testRuntime"(junitJupiter("junit-jupiter-engine"))
        "testRuntime"(create(group = "org.junit.platform", name = "junit-platform-launcher", version = "1.1.0-M1"))
    }

    junitPlatform {
        details = Details.VERBOSE
    }

    spotless {
        java {
            googleJavaFormat()
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            ktlint(Versions.KTLINT)
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.isIncremental = true
    }
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.3.1"
    distributionType = Wrapper.DistributionType.ALL
}

/**
 * Configures the [junitPlatform][org.junit.platform.gradle.plugin.JUnitPlatformExtension] project extension.
 */
fun Project.`junitPlatform`(configure: org.junit.platform.gradle.plugin.JUnitPlatformExtension.() -> Unit) =
    extensions.configure("junitPlatform", configure)
