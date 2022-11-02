package com.dirzaaulia.allaboutvalorant.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.visible(visibility: Boolean): Modifier = this.then(alpha(if (visibility) 1f else 0f))

fun String?.replaceIfNull(defaultValue: String = ""): String {
  if (this == null)
    return defaultValue
  return this
}