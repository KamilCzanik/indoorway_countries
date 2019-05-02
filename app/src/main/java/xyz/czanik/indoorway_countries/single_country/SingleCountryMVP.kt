package xyz.czanik.indoorway_countries.single_country

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener

interface SingleCountryMVP {

    interface View {
        val countryName: String

        fun showMessage(message: String)
    }

    interface Presenter {
        val view: View
        val model: Model

        fun prepareView()
    }

    interface Model {
        val country: ComplexCountry

        fun loadCountryData(countryName: String,loadingListener: OnLoadingCompleteListener)
    }
}