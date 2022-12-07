package com.dirzaaulia.countries.data.model

import androidx.annotation.Keep
import com.dirzaaulia.countries.data.model.Timezone.Companion.setDataMap
import com.dirzaaulia.countries.data.model.Transalations.Companion.setDataMap
import com.dirzaaulia.countries.utils.parseJsonToObject
import com.dirzaaulia.countries.utils.replaceIfNull
import com.squareup.moshi.Json

@Keep
data class Country(

	@Json(name="id")
	val id: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="iso3")
	val iso3: String? = null,

	@Json(name="numeric_code")
	val numericCode: String? = null,

	@Json(name="iso2")
	val iso2: String? = null,

	@Json(name="phonecode")
	val phonecode: String? = null,

	@Json(name="capital")
	val capital: String? = null,

	@Json(name="currency")
	val currency: String? = null,

	@Json(name="currency_name")
	val currencyName: String? = null,

	@Json(name="currency_symbol")
	val currencySymbol: String? = null,

	@Json(name="tld")
	val tld: String? = null,

	@Json(name="native")
	val native: String? = null,

	@Json(name="region")
	val region: String? = null,

	@Json(name="subregion")
	val subregion: String? = null,

	@Json(name="timezones")
	val timezones: String? = null,

	@Json(name="translations")
	val translations: String? = null,

	@Json(name="latitude")
	val latitude: String? = null,

	@Json(name="longitude")
	val longitude: String? = null,

	@Json(name="emoji")
	val emoji: String? = null,

	@Json(name="emojiU")
	val emojiU: String? = null,
) {
	companion object {

		fun parseTimezoneFromJson(json: String?): Map<String, String>? {
			val timezone = json?.replace("""[\[\]]""".toRegex(), "")
				?.parseJsonToObject<Timezone>()
			return timezone?.let { timezone.setDataMap() }
		}

		fun parseTranslationsFromJson(json: String?): Map<String, String>? {
			val translations = json?.replace("""[\[\]]""".toRegex(), "")
				?.parseJsonToObject<Transalations>()
			return translations?.let { setDataMap(it) }
		}

		fun setDataMap(data: Country): Map<String, String> {
			return mapOf(
				"Flag" to "https://countryflagsapi.com/png/${data.iso3}",
				"Phone Code" to data.phonecode.replaceIfNull("-"),
				"Capital" to data.capital.replaceIfNull("-"),
				"Currency" to data.currency.replaceIfNull("-"),
				"Currency Name" to data.currencyName.replaceIfNull("-"),
				"Currency Symbol" to data.currencySymbol.replaceIfNull("-"),
				"Native" to data.native.replaceIfNull("-"),
				"Region" to data.region.replaceIfNull("-"),
				"Sub Region" to data.subregion.replaceIfNull("-"),
				"Emoji" to data.emoji.replaceIfNull("-"),
				"Emoji U" to data.emojiU.replaceIfNull("-")
			)
		}
	}
}
