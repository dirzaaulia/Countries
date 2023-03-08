import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Accompanist {
        private const val animation = "com.google.accompanist:accompanist-navigation-animation:${Version.accompanist}"
        private const val flowLayout = "com.google.accompanist:accompanist-flowlayout:${Version.accompanist}"
        private const val insets = "com.google.accompanist:accompanist-insets:${Version.accompanist}"
        private const val pager = "com.google.accompanist:accompanist-pager:${Version.accompanist}"
        private const val placeholderUI = "com.google.accompanist:accompanist-placeholder-material:${Version.accompanist}"
        private const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Version.accompanist}"
        private const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Version.accompanist}"
        private const val web = "com.google.accompanist:accompanist-webview:${Version.accompanist}"

        val implementation = arrayListOf<String>().apply {
            add(animation)
            add(flowLayout)
            add(insets)
            add(pager)
            add(placeholderUI)
            add(swipeRefresh)
            add(systemUiController)
            add(web)
        }
    }

    object AndroidX {
        private const val activityCompose = "androidx.activity:activity-compose:${Version.activityCompose}"
        private const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:${Version.constraintLayoutCompose}"
        private const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
        private const val splashScreen = "androidx.core:core-splashscreen:${Version.coreSplashScreen}"
        private const val navigation = "androidx.navigation:navigation-compose:${Version.navigationCompose}"
        private const val webkit = "androidx.webkit:webkit:${Version.webkit}"

        val implementation = arrayListOf<String>().apply {
            add(activityCompose)
            add(constraintLayoutCompose)
            add(coreKtx)
            add(splashScreen)
            add(navigation)
            add(webkit)
        }

        object Compose {
            private const val animation = "androidx.compose.animation:animation:${Version.composeAnimation}"
            private const val foundation = "androidx.compose.foundation:foundation:${Version.composeFoundation}"
            private const val material = "androidx.compose.material:material-icons-extended:${Version.composeMaterial}"
            private const val material3 = "androidx.compose.material3:material3:${Version.composeMaterial3}"
            private const val runtime = "androidx.compose.runtime:runtime:${Version.composeRuntime}"
            private const val ui = "androidx.compose.ui:ui:${Version.composeUi}"
            private const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.composeUi}"

            val implementation = arrayListOf<String>().apply {
                add(animation)
                add(foundation)
                add(material)
                add(material3)
                add(runtime)
                add(ui)
                add(uiTooling)
            }
        }

        object Lifecycle {
            private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycle}"

            val implementation = arrayListOf<String>().apply {
                add(viewModel)
            }
        }

        object Test {
            private const val core = "androidx.test:core:${Version.test}"
            private const val rules = "androidx.test:rules:${Version.test}"
            private const val jUnit = "androidx.test.ext:junit-ktx:${Version.extJUnit}"
            private const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espresso}"

            val androidTestImplementation = arrayListOf<String>().apply {
                add(core)
                add(rules)
                add(jUnit)
                add(espressoCore)
            }
        }
    }

    object Chucker {
        private const val debug = "com.github.chuckerteam.chucker:library:${Version.chucker}"
        private const val release = "com.github.chuckerteam.chucker:library-no-op:${Version.chucker}"

        val debugImplementation = arrayListOf<String>().apply {
            add(debug)
        }

        val releaseImplementation = arrayListOf<String>().apply {
            add(release)
        }
    }

    object Coil {
        private const val coil = "io.coil-kt:coil-compose:${Version.coil}"

        val implementation = arrayListOf<String>().apply {
            add(coil)
        }
    }

    object Coroutines {
        private const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
        private const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        private const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Version.coroutines}"

        val implementation = arrayListOf<String>().apply {
            add(android)
            add(core)
            add(playServices)
        }
    }

    object Hilt {
        private const val android = "com.google.dagger:hilt-android:${Version.hilt}"
        private const val compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
        private const val navigation = "androidx.hilt:hilt-navigation-compose:${Version.hiltCompose}"

        val implementation = arrayListOf<String>().apply {
            add(android)
            add(navigation)
        }

        val kapt = arrayListOf<String>().apply {
            add(compiler)
        }
    }

    object JUnit {
        private const val junit = "junit:junit:${Version.jUnit}"

        val androidTestImplementation = arrayListOf<String>().apply {
            add(junit)
        }
    }

    object Kotlin {
        private const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.reflect}"

        val implementation = arrayListOf<String>().apply {
            add(reflect)
        }
    }

    object Material {
        private const val material = "com.google.android.material:material:${Version.material}"

        val implementation = arrayListOf<String>().apply {
            add(material)
        }
    }

    object Other {
        private const val timber = "com.jakewharton.timber:timber:4.7.1"

        val implementation = arrayListOf<String>().apply {
            add(timber)
        }
    }

    object Paging {
        private const val compose = "androidx.paging:paging-compose:${Version.pagingCompose}"
        private const val runtime = "androidx.paging:paging-runtime-ktx:${Version.paging}"

        val implementation = arrayListOf<String>().apply {
            add(compose)
            add(runtime)
        }
    }

    object SquareUp {
        private const val core = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        private const val kotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
        private const val logging = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"

        val implementation = arrayListOf<String>().apply {
            add(core)
            add(kotlin)
            add(logging)
        }
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}

fun DependencyHandler.releaseImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("releaseImplementation", dependency)
    }
}