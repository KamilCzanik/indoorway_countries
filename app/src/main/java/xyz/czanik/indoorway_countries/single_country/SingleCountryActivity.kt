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
import xyz.czanik.indoorway_countries.di.single_country.DaggerSingleCountryComponent
import xyz.czanik.indoorway_countries.di.single_country.SingleCountryModule
import javax.inject.Inject

class SingleCountryActivity : MessageAppCompatActivity(),SingleCountryMVP.View,OnMapReadyCallback {

    @Inject override lateinit var presenter: SingleCountryMVP.Presenter
    override val countryCode by lazy { intent.getStringExtra(INTENT_COUNTRY_CODE) as String }

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
        val zoom = CameraUpdateFactory.zoomTo(5.0F)

        map.moveCamera(countryPos)
        map.moveCamera(zoom)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_country)
        mapView.onCreate(savedInstanceState)

        DaggerSingleCountryComponent.builder()
            .singleCountryModule(SingleCountryModule(this))
            .build().inject(this)

        presenter.prepareView()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        private const val INTENT_COUNTRY_CODE = "country_code"
        fun createIntent(context: Context,countryCode: String) : Intent {
            return Intent(context, SingleCountryActivity::class.java).apply {
                putExtra(INTENT_COUNTRY_CODE, countryCode)
            }
        }
    }
}
