package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import android.content.Intent
import android.net.Uri.parse
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou.justLoadImage
import com.google.android.gms.maps.CameraUpdateFactory.newLatLng
import com.google.android.gms.maps.CameraUpdateFactory.zoomTo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_single_country.*
import xyz.czanik.indoorway_countries.MapAppCompatActivity
import xyz.czanik.indoorway_countries.di.single_country.DaggerSingleCountryComponent
import xyz.czanik.indoorway_countries.di.single_country.SingleCountryModule
import javax.inject.Inject

/** Aktywność wyświetlająca informacje o kraju.
 * Implementuje funkcjonalność SingleCountryMVP.View*/
class SingleCountryActivity : MapAppCompatActivity(),SingleCountryMVP.View,OnMapReadyCallback {

    @Inject override lateinit var presenter: SingleCountryMVP.Presenter
    override val countryCode by lazy { intent.getStringExtra(INTENT_COUNTRY_CODE) as String }

    override var country: ComplexCountry = ComplexCountry()
       set(value) {
           field = value
           with(/*country*/value) {
               supportActionBar?.title = name
               countryDetails.text = details
               justLoadImage(this@SingleCountryActivity, parse(flag), countryFlagView)
           }
           mapView.getMapAsync(this)
        }

    override fun onMapReady(map: GoogleMap) {
        with(map) {
            moveCamera(newLatLng(country.getLatLng()))
            moveCamera(zoomTo(5.0F))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setHomeButtonEnabled(true)
        injectDependencies()
        presenter.prepareView()
    }

    private fun injectDependencies() {
        DaggerSingleCountryComponent.builder()
            .singleCountryModule(SingleCountryModule(this))
            .build().inject(this)
    }

    override fun setLoadingPanelVisibility(visible: Boolean) {
        val visibility = if(visible) VISIBLE else GONE
        bigFlagLoadingPanel.visibility = visibility
        mapLoadingPanel.visibility = visibility
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
