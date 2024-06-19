plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.io.gitlab.arturbosch.detekt)
    alias(libs.plugins.com.diffplug.spotless)
    alias(libs.plugins.com.vanniktech.maven.publish)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.com.diffplug.spotless.get().pluginId)
    apply(plugin = rootProject.libs.plugins.io.gitlab.arturbosch.detekt.get().pluginId)

    spotless {
        kotlin {
            target("**/*.kt")
            ktlint("1.3.0")
        }
    }
}