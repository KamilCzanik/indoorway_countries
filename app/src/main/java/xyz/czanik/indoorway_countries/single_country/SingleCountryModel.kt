package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import xyz.czanik.indoorway_countries.R
import javax.inject.Inject

/** Klasa odpowiedzialna za dostarczenie danych pobranych z REST API*/
class SingleCountryModel @Inject constructor(private val context: Context) : SingleCountryMVP.Model {

    override lateinit var country: ComplexCountry

    private val NAME = "name"
    private val FLAG = "flag"
    private val REGION = "region"
    private val LATLNG = "latlng"
    private val CAPITAL = "capital"
    private val POPULATION = "population"

    override fun loadCountryData(countryCode: String, loadingListener: OnLoadingCompleteListener) {
        val countryUri = "https://restcountries.eu/rest/v2/alpha/$countryCode?fields=$NAME;$FLAG;$REGION;$CAPITAL;$LATLNG;$POPULATION"

        val request = JsonObjectRequest(
            Request.Method.GET,
            countryUri,
            null,
            Response.Listener<JSONObject> { json ->
                try {
                    country = parseJsonToComplexCountry(json,countryCode)
                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }

    private fun parseJsonToComplexCountry(jsonObject: JSONObject, countryCode: String) : ComplexCountry {
        val default = context.resources.getString(R.string.unknown)
        return ComplexCountry(
            jsonObject.getStringOrDefault(NAME,default),
            jsonObject.getStringOrDefault(FLAG,default),
            countryCode,
            jsonObject.getStringOrDefault(CAPITAL,default),
            jsonObject.getStringOrDefault(REGION,default),
            jsonObject.getStringOrDefault(POPULATION,default),
            jsonObject.getJSONArray(LATLNG).toLatLng())
    }


}

/** Opakowanie kodu służącego do wydobycia danych z JSON w celu zwiększenia czytelności*/

fun JSONArray.toLatLng() = if(length() != 0) LatLng(getDouble(0),getDouble(1)) else LatLng(0.0,0.0)

fun JSONObject.getStringOrDefault(key: String,default: String): String = if(getString(key).isNotEmpty()) getString(key) else default