package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_single_country.*
import xyz.czanik.indoorway_countries.MessageAppCompatActivity
import xyz.czanik.indoorway_countries.R
import javax.inject.Inject

class SingleCountryActivity : MessageAppCompatActivity(),SingleCountryMVP.View,OnMapReadyCallback {

    @Inject override lateinit var presenter: SingleCountryMVP.Presenter
    override val countryName by lazy { intent.getStringExtra(INTENT_COUNTRY_NAME) as String }

    override var country: ComplexCountry = ComplexCountry()
       set(country) {
           field = country
           countryNameView.text = country.name
           GlideToVectorYou.justLoadImage(this, Uri.parse(country.flag), countryFlag)
           countryDetails.text = country.details
           mapView.getMapAsync(this)
        }

    override fun onMapReady(map: GoogleMap) {
        val countryPos = CameraUpdateFactory.newLatLng(country.latLng)
        map.moveCamera(countryPos)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_country)
        mapView.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        private const val INTENT_COUNTRY_NAME = "country_name"
        fun createIntent(context: Context,name: String) : Intent {
            return Intent(context, SingleCountryActivity::class.java).apply {
                putExtra(INTENT_COUNTRY_NAME, name)
            }
        }
    }
}
