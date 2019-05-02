package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener

class SingleCountryModel(private val context: Context) : SingleCountryMVP.Model {

    override lateinit var country: ComplexCountry

    private val FLAG = "flag"
    private val REGION = "region"
    private val CODE = "alpha2Code"
    private val LATLNG = "latlng"
    private val CAPITAL = "captital"

    override fun loadCountryData(countryName: String, loadingListener: OnLoadingCompleteListener) {
        val countryUri = "https://restcountries.eu/rest/v2/name/$countryName?$FLAG;$REGION;$CODE;$LATLNG;$CAPITAL"

        val request = JsonObjectRequest(
            Request.Method.GET,
            countryUri,
            null,
            Response.Listener<JSONObject> { json ->
                try {
                    val llArr = json.getJSONArray(LATLNG)

                    country = ComplexCountry(
                        countryName,
                        json.getString(FLAG),
                        json.getString(CODE),
                        json.getString(CAPITAL),
                        json.getString(REGION),
                        LatLng(llArr.getDouble(0),llArr.getDouble(1)))

                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}