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

fun provideAdapter(): Moshi =  Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

inline fun <reified T> String.parseList(): List<T>? {
  val moshi = provideAdapter()
  val type = Types.newParameterizedType(List::class.java, T::class.java)
  val adapter: JsonAdapter<List<T>> = moshi
    .adapter<List<T>?>(type)
    .lenient()

  return adapter.fromJson(this)
}

inline fun <reified T> String.parseJsonToObject(): T? {
  val moshi = provideAdapter()

  return let {
    moshi
      .adapter(T::class.java)
      .lenient()?.fromJson(this)
  }
}