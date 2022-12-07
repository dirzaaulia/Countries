package com.dirzaaulia.countries.data.model

import androidx.annotation.Keep
import com.dirzaaulia.countries.utils.parseJsonToObject
import com.dirzaaulia.countries.utils.replaceIfNull
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Timezone(

  @Json(name = "zoneName")
  val zoneName: String? = null,

  @Json(name = "gmtOffsetName")
  val gmtOffsetName: String? = null,

  @Json(name = "abbreviation")
  val abbreviation: String? = null,

  @Json(name = "tzName")
  val timezoneName: String? = null,
) {
  companion object {
    fun Timezone.setDataMap(): Map<String, String> {
      return mapOf(
        "Zone Name" to this.zoneName.replaceIfNull("-"),
        "GMT Offset Name" to this.gmtOffsetName.replaceIfNull("-"),
        "Abbreviation" to this.abbreviation.replaceIfNull("-"),
        "Timezone Name" to this.timezoneName.replaceIfNull("-")
      )
    }
  }
}