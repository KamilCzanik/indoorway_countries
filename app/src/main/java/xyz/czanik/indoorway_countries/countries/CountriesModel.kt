package xyz.czanik.indoorway_countries.countries

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject

class CountriesModel @Inject constructor(private val context: Context) : CountriesMVP.Model {
    override val countries = ArrayList<CountryItem>()
    private val countriesUrl = "https://restcountries.eu/rest/v2/all?fields=name;flag"

    override fun loadCountries(loadingListener: CountriesMVP.Model.OnLoadingCompleteListener) {
        val request = JsonArrayRequest(Request.Method.GET,countriesUrl,null,
            Response.Listener<JSONArray> { jsonArray ->
                try {
                    for(i in 0 until jsonArray.length()) {
                        val country = jsonArray.getJSONObject(i)
                        val name = country.getString("name")
                        val flag = country.getString("flag")
                        countries += CountryItem(name,flag)
                    }
                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}