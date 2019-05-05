package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import org.json.JSONException
import org.json.JSONObject
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import xyz.czanik.indoorway_countries.toJsonString
import javax.inject.Inject

/** Klasa odpowiedzialna za dostarczenie danych pobranych z REST API*/
class SingleCountryModel @Inject constructor(private val context: Context) : SingleCountryMVP.Model {

    override lateinit var country: ComplexCountry

    override fun loadCountryData(countryCode: String, loadingListener: OnLoadingCompleteListener) {
        val countryUri = "https://restcountries.eu/rest/v2/alpha/$countryCode?fields=name;flag;alpha3Code;capital;region;population;latlng"

        val request = JsonObjectRequest(
            Request.Method.GET,
            countryUri,
            null,
            Response.Listener<JSONObject> { json ->
                try {
                    country = Klaxon().parse<ComplexCountry>(json.toJsonString()) ?: ComplexCountry()
                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}
