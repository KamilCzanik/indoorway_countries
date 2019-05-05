package xyz.czanik.indoorway_countries.single_country

import com.beust.klaxon.Json
import com.google.android.gms.maps.model.LatLng

/** Klasa przechowująca podstawowe informacje potrzebne do wyświetlenie informacji kraju w aktywności*/
data class ComplexCountry(
    val name: String = "",
    val flag: String = "",
    @Json(name = "alpha3Code") val code: String = "",
    val capital: String = "",
    val region: String = "",
    val population: String = "",
    val latlng: DoubleArray = doubleArrayOf(0.0,0.0)){

    fun getLatLng() = LatLng(latlng[0],latlng[1])

    val details =
                " Code : $code \n" +
                " Capital city : $capital \n" +
                " Region : $region \n" +
                " Population : $population"
}