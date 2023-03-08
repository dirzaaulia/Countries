plugins {
  id("com.android.application") version Version.toolsBuildGradle apply false
  id("org.jetbrains.kotlin.android") version Version.kotlinGradle apply false
  id("org.jetbrains.kotlin.kapt") version Version.kotlinGradle apply false
  id("org.jetbrains.kotlin.plugin.parcelize") version Version.kotlinGradle apply false
  id("com.google.dagger.hilt.android") version Version.hilt apply false
  id("com.diffplug.spotless") version Version.spotless apply false
}

tasks.register("clean",Delete::class){
  delete(rootProject.buildDir)
}