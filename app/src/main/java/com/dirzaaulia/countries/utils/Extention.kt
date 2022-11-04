package com.dirzaaulia.countries.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.core.text.HtmlCompat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

fun Modifier.visible(visibility: Boolean): Modifier = this.then(alpha(if (visibility) 1f else 0f))

fun String?.replaceIfNull(defaultValue: String = ""): String {
  if (this == null)
    return defaultValue
  return this
}

fun String.unicodeEmojiToHtmlEmoji(): CharSequence {
  val inEmojiPrefix = "U+"
  val outEmojiPrefix = "&#x"
  val outEmojiSuffix = ";"
  return try {
    HtmlCompat.fromHtml(
      this.replace(
        inEmojiPrefix,
        outEmojiPrefix,
        true
      ) + outEmojiSuffix,
      HtmlCompat.FROM_HTML_MODE_LEGACY
    )
  } catch (e: Throwable) {
    ""
  }
}

inline fun <reified T> String.parseList(): List<T> {
  val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
  val type = Types.newParameterizedType(List::class.java, T::class.java)
  val adapter: JsonAdapter<List<T>> = moshi.adapter(type)

  return adapter.fromJson(this) ?: emptyList()
}

inline fun <reified T> String.parseJsonToObject(): T? {
  val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  return let { moshi.adapter(T::class.java)?.fromJson(this) }
}