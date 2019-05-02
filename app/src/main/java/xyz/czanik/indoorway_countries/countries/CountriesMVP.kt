package xyz.czanik.indoorway_countries.countries

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener

interface CountriesMVP {

    interface View {
        val presenter: Presenter
        var recyclerAdapter: CountriesRecyclerViewAdapter

        fun startSingleCountryActivityFor(name: String)
        fun showMessage(message: String)
    }

    interface Presenter {
        val view: View
        val model: Model

        fun prepareView()
        fun onSearch(input: String)
    }

    interface Model {
        val countries: ArrayList<CountryItem>

        fun loadCountries(loadingListener: OnLoadingCompleteListener)
    }
}