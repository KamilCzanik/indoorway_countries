package xyz.czanik.indoorway_countries.single_country

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import javax.inject.Inject

/** Presenter odpowiedzialny za logikę oraz spojenie funkcjonalności View i Modelu dostarczającego danych*/
class SingleCountryPresenter @Inject constructor(
    override val view: SingleCountryMVP.View,
    override val model: SingleCountryMVP.Model) : SingleCountryMVP.Presenter {

    override fun prepareView() { model.loadCountryData(view.countryCode,loadingListener) }

    val loadingListener = object : OnLoadingCompleteListener {

        override fun onComplete() {
            view.country = model.country
            view.setLoadingPanelVisibility(false)
        }

        override fun onFailure(message: String) { view.showMessage(message) }
    }
}