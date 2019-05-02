package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONException
import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import javax.inject.Inject

class SingleCountryModel @Inject constructor(private val context: Context) : SingleCountryMVP.Model {

    override lateinit var country: ComplexCountry

    private val FLAG = "flag"
    private val REGION = "region"
    private val CODE = "alpha2Code"
    private val LATLNG = "latlng"
    private val CAPITAL = "capital"

    override fun loadCountryData(countryName: String, loadingListener: OnLoadingCompleteListener) {
        val countryUri = "https://restcountries.eu/rest/v2/name/$countryName?fields=$FLAG;$REGION;$CODE;$CAPITAL;$LATLNG"
        Log.d("COUNTRY_URI",countryUri)
        val request = JsonArrayRequest(
            Request.Method.GET,
            countryUri,
            null,
            Response.Listener<JSONArray> { json ->
                try {
                    val countryObject = json.getJSONObject(0)
                    val llArr = countryObject.getJSONArray(LATLNG)

                    country = ComplexCountry(
                        countryName,
                        countryObject.getString(FLAG),
                        countryObject.getString(CODE),
                        countryObject.getString(CAPITAL),
                        countryObject.getString(REGION),
                        LatLng(llArr.getDouble(0),llArr.getDouble(1)))

                    loadingListener.onComplete()
                } catch (e: JSONException) { loadingListener.onFailure(e.message!!) }
            },
            Response.ErrorListener { loadingListener.onFailure(it.message!!) })

        Volley.newRequestQueue(context).add(request)
    }
}