package xyz.czanik.indoorway_countries.countries

import xyz.czanik.indoorway_countries.countries.CountriesMVP
import xyz.czanik.indoorway_countries.countries.CountriesMVP.Model.OnLoadingCompleteListener
import xyz.czanik.indoorway_countries.countries.CountriesRecyclerViewAdapter
import xyz.czanik.indoorway_countries.countries.CountriesRecyclerViewAdapter.OnItemClickListener
import javax.inject.Inject

class CountriesPresenter @Inject constructor(
    override val view: CountriesMVP.View,
    override val model: CountriesMVP.Model) : CountriesMVP.Presenter {

    val recyclerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(itemPos: Int) {
            //view.startSingleCountryActivityFor(model.countries[itemPos].name)
            view.showMessage("Hello $itemPos")
        }
    }

    val loadingCompleteListener = object : OnLoadingCompleteListener {
        override fun onComplete() {
            view.recyclerAdapter = CountriesRecyclerViewAdapter(view as CountriesActivity,recyclerItemClickListener)
            view.recyclerAdapter.countries = model.countries
        }

        override fun onFailure(message: String) {
            view.showMessage(message)
        }
    }

    override fun prepareView() {
        model.loadCountries(loadingCompleteListener)
    }

    override fun onSearch(input: String) {
        val filteredCountries = model.countries.filter { it.name.toLowerCase().contains(input.toLowerCase()) }
        view.recyclerAdapter.countries = filteredCountries
    }
}