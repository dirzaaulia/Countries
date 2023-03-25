package com.dirzaaulia.countries.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class Transalations(

  @Json(name = "kr")
  val kr: String? = null,

  @Json(name= "pt-BR")
  val br: String? = null,

  @Json(name = "pt")
  val pt: String? = null,

  @Json(name = "nl")
  val nl: String? = null,

  @Json(name = "hr")
  val hr: String? = null,

  @Json(name = "fa")
  val fa: String? = null,

  @Json(name = "de")
  val de: String? = null,

  @Json(name = "es")
  val es: String? = null,

  @Json(name = "ja")
  val ja: String? = null,

  @Json(name = "it")
  val it: String? = null,

  @Json(name = "cn")
  val cn: String? = null,

  @Json(name = "tr")
  val tr: String? = null
) {
  companion object {

    fun setDataMap(data: Transalations): Map<String, String?> {
      return mapOf(
        "Korean" to data.kr,
        "Portugese ( Brazil )" to data.br,
        "Portugese" to data.pt,
        "Dutch" to data.nl,
        "Croatian" to data.hr,
        "Farsi" to data.fa,
        "Denmark" to data.de,
        "Spanish" to data.es,
        "Japanese" to data.ja,
        "Italian" to data.it,
        "Chinese" to data.cn,
        "Turki" to data.tr
      )
    }
  }
}