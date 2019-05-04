package xyz.czanik.indoorway_countries.countries

import xyz.czanik.indoorway_countries.OnLoadingCompleteListener
import xyz.czanik.indoorway_countries.countries.CountriesRecyclerViewAdapter.OnItemClickListener
import javax.inject.Inject

/** Presenter odpowiedzialny za logikę oraz spojenie funkcjonalności View i Modelu dostarczającego danych*/
class CountriesPresenter @Inject constructor(
    override val view: CountriesMVP.View,
    override val model: CountriesMVP.Model) : CountriesMVP.Presenter {

    /** W momencie wywołania funkcji onSearch, presenter filtruje dostępne kraje i przekazuje je adapterowi*/
    override fun onSearch(input: String) {
        val filteredCountries = model.countries.getCountriesWith(input)
        view.recyclerAdapter.countries = filteredCountries
    }

    override fun prepareView() {
        model.loadCountries(loadingCompleteListener)
    }

    /** loadingListener w postaci pola odpowiada za obsłużenie wyniku wczytywania danych z modelu.
     *  Mógłby on mieć postać anonimowej implementacji,ale w dużym stopniu zmniejszyłoby to testowalność zawartego
     *  w nim kodu*/
    val loadingCompleteListener = object : OnLoadingCompleteListener {
        override fun onComplete() {
            view.recyclerAdapter = CountriesRecyclerViewAdapter(view as CountriesActivity,recyclerItemClickListener)
            view.recyclerAdapter.countries = model.countries
        }

        override fun onFailure(message: String) {
            view.showMessage(message)
        }
    }

    /** recyclerItemClickListener w postaci pola odpowiada za obsłużenie interakcji z elementami
     * znajdującymi się w recyclerView*/
    val recyclerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(itemPos: Int) {
            view.startSingleCountryActivityFor(view.recyclerAdapter.countries[itemPos].code)
        }
    }

}

/** Opakowanie funkcji filter wyszukującej kraje zgodne z oczekiwaniami użytkownika w celu zwiększenia czytelności kodu*/
fun List<CountryItem>.getCountriesWith(string: String) = filter { it.name.toLowerCase().contains(string.toLowerCase()) }