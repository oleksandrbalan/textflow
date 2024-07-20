import com.diffplug.gradle.spotless.SpotlessPlugin
import io.gitlab.arturbosch.detekt.DetektPlugin

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
    alias(libs.plugins.mavenpublish)
}

subprojects {
    apply<DetektPlugin>()
    apply<SpotlessPlugin>()

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
                        "ktlint_standard_function-signature" to "disabled",
                        "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    ),
                )
        }
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.libraries)
    }
}
