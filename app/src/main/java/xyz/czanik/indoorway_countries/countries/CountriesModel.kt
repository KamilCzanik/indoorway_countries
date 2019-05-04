package xyz.czanik.indoorway_countries.countries

import android.content.Context
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import javax.inject.Inject

/** Klasa odpowiedzialna za dostarczenie danych pobranych z REST API*/
class CountriesModel @Inject constructor(private val context: Context) : CountriesMVP.Model {

    override val countries = ArrayList<CountryItem>()

    private val NAME = "name"
    private val FLAG = "flag"
    private val CODE = "alpha3Code"
    private val countriesUrl = "https://restcountries.eu/rest/v2/all?fields=$NAME;$FLAG;$CODE"

    override fun loadCountries(loadingListener: OnLoadingCompleteListener) {
        val request = JsonArrayRequest(
            GET,
            countriesUrl,
            null,
            Response.Listener<JSONArray> { jsonArray ->
                try {
                    for(i in 0 until jsonArray.length())
                        countries += parseJsonToCountryItem(jsonArray.getJSONObject(i))

                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }

    private fun parseJsonToCountryItem(json: JSONObject) =
        CountryItem(
        json.getString(NAME),
        json.getString(FLAG),
        json.getString(CODE))
}