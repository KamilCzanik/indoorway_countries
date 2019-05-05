package xyz.czanik.indoorway_countries.countries

import com.beust.klaxon.Json

/** Klasa przechowująca podstawowe informacje potrzebne do wyświetlenie przedmiotu w recyclerView*/
data class CountryItem(
    val name: String = "",
    val flag: String = "",
    @Json(name = "alpha3Code") val code: String = "")