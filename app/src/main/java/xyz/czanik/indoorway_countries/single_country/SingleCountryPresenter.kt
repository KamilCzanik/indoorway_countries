package xyz.czanik.indoorway_countries.single_country

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import javax.inject.Inject

class SingleCountryPresenter @Inject constructor(
    override val view: SingleCountryMVP.View,
    override val model: SingleCountryMVP.Model) : SingleCountryMVP.Presenter {

    val loadingListener = object : OnLoadingCompleteListener {
        override fun onComplete() {
            view.country = model.country
        }

        override fun onFailure(message: String) {
            view.showMessage(message)
        }
    }

    override fun prepareView() {
        model.loadCountryData(view.countryCode,loadingListener)
    }
}