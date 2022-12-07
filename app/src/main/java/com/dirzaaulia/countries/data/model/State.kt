package com.dirzaaulia.countries.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class State(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "iso2")
    val iso2: String? = null
) {
}