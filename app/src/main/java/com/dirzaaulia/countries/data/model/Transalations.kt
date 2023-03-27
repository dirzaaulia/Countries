package com.dirzaaulia.countries.data.model

import androidx.annotation.Keep
import com.dirzaaulia.countries.utils.replaceIfNull
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

    fun setDataMap(data: Transalations): Map<String, String> {
      return mapOf(
        "Korean" to data.kr.replaceIfNull(),
        "Portugese ( Brazil )" to data.br.replaceIfNull(),
        "Portugese" to data.pt.replaceIfNull(),
        "Dutch" to data.nl.replaceIfNull(),
        "Croatian" to data.hr.replaceIfNull(),
        "Farsi" to data.fa.replaceIfNull(),
        "Denmark" to data.de.replaceIfNull(),
        "Spanish" to data.es.replaceIfNull(),
        "Japanese" to data.ja.replaceIfNull(),
        "Italian" to data.it.replaceIfNull(),
        "Chinese" to data.cn.replaceIfNull(),
        "Turki" to data.tr.replaceIfNull()
      )
    }
  }
}