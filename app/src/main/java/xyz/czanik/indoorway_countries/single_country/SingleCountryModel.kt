package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import javax.inject.Inject

class SingleCountryModel @Inject constructor(private val context: Context) : SingleCountryMVP.Model {

    override lateinit var country: ComplexCountry

    private val NAME = "name"
    private val FLAG = "flag"
    private val REGION = "region"
    private val LATLNG = "latlng"
    private val CAPITAL = "capital"

    override fun loadCountryData(countryCode: String, loadingListener: OnLoadingCompleteListener) {
        val countryUri = "https://restcountries.eu/rest/v2/alpha/$countryCode?fields=$NAME;$FLAG;$REGION;$CAPITAL;$LATLNG"
        Log.d("COUNTRY_URI",countryUri)
        val request = JsonObjectRequest(
            Request.Method.GET,
            countryUri,
            null,
            Response.Listener<JSONObject> { json ->
                try {
                    val llArr = json.getJSONArray(LATLNG)

                    country = ComplexCountry(
                        json.getString(NAME),
                        json.getString(FLAG),
                        countryCode,
                        json.getString(CAPITAL),
                        json.getString(REGION),
                        llArr.getLatLng())

                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}

fun JSONArray.getLatLng() = if(length() != 0) LatLng(getDouble(0),getDouble(1)) else LatLng(0.0,0.0)