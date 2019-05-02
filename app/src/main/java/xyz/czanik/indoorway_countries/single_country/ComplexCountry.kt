package xyz.czanik.indoorway_countries.single_country

import com.google.android.gms.maps.model.LatLng

data class ComplexCountry(
    val name: String,
    val flag: String,
    val code: String,
    val capitalCity: String,
    val region: String,
    val latLng: LatLng)