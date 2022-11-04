package com.dirzaaulia.countries.data.model

import com.dirzaaulia.countries.data.model.Timezone.Companion.setDataMap
import com.dirzaaulia.countries.utils.parseJsonToObject
import com.squareup.moshi.Json

data class Transalations(

  @Json(name = "kr")
  val kr: String,

  @Json(name= "pt-BR")
  val br: String,

  @Json(name = "pt")
  val pt: String,

  @Json(name = "nl")
  val nl: String,

  @Json(name = "hr")
  val hr: String,

  @Json(name = "fa")
  val fa: String,

  @Json(name = "de")
  val de: String,

  @Json(name = "es")
  val es: String,

  @Json(name = "ja")
  val ja: String,

  @Json(name = "it")
  val it: String,

  @Json(name = "cn")
  val cn: String,

  @Json(name = "tr")
  val tr: String
) {
  companion object {

    fun Transalations.setDataMap(data: Transalations): Map<String, String> {
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