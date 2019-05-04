package xyz.czanik.indoorway_countries.single_country

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener

/** Kontrakt MVP definiujący zadania każdego elementu Modelu,View i Presentera*/
interface SingleCountryMVP {

    interface View {
        val presenter: Presenter
        val countryCode: String
        var country: ComplexCountry

        fun showMessage(message: String)
    }

    interface Presenter {
        val view: View
        val model: Model

        fun prepareView()
    }

    interface Model {
        val country: ComplexCountry

        fun loadCountryData(countryCode: String,loadingListener: OnLoadingCompleteListener)
    }
}