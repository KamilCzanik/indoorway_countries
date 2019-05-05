package xyz.czanik.indoorway_countries.countries

import android.content.Context
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import org.json.JSONArray
import org.json.JSONException
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import xyz.czanik.indoorway_countries.toJsonString
import javax.inject.Inject

/** Klasa odpowiedzialna za dostarczenie danych pobranych z REST API*/
class CountriesModel @Inject constructor(private val context: Context) : CountriesMVP.Model {

    override val countries = ArrayList<CountryItem>()
    private val countriesUrl = "https://restcountries.eu/rest/v2/all?fields=name;flag;alpha3Code"

    override fun loadCountries(loadingListener: OnLoadingCompleteListener) {
        val request = JsonArrayRequest(
            GET,
            countriesUrl,
            null,
            Response.Listener<JSONArray> { jsonArray ->
                try {
                    for (i in 0 until jsonArray.length())
                        countries += Klaxon().parse<CountryItem>(jsonArray.getJSONObject(i).toJsonString()) ?: CountryItem()

                    loadingListener.onComplete()
                } catch (e: JSONException) {
                    loadingListener.onFailure(e.message!!)
                }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}