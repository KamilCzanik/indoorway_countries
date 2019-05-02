package xyz.czanik.indoorway_countries.single_country

import com.google.android.gms.maps.model.LatLng

data class ComplexCountry(
    val name: String = "",
    val flagUri: String = "",
    val code: String = "",
    val capitalCity: String = "",
    val region: String = "",
    val latLng: LatLng = LatLng(0.0,0.0)) {

    val details =
                " Code : $code \n" +
                " Capital city : $capitalCity \n" +
                " Region : $region \n" +
                " co-ordinates : ${latLng.latitude} | ${latLng.longitude}"
}