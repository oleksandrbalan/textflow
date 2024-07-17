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
                .editorConfigOverride(
                    mapOf(
                        "ij_kotlin_allow_trailing_comma" to "true",
                        "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
                        "ktlint_code_style" to "android_studio",
                        "ktlint_standard_property-naming" to "disabled",
                        "ktlint_standard_class-signature" to "disabled",
                        "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    ),
                )
        }
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.libraries)
    }
}
