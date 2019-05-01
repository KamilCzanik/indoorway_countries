package xyz.czanik.indoorway_countries.countries

interface CountriesMVP {

    interface View {
        val presenter: Presenter
        var recyclerAdapter: CountriesRecyclerViewAdapter
    }

    interface Presenter {
        val view: View
        val model: Model

        fun onSearch(input: String)
    }

    interface Model {
        val countries: ArrayList<CountryItem>

        fun loadCountriesNames(loadingListener: OnLoadingCompleteListener)

        interface OnLoadingCompleteListener {
            fun onComplete()
            fun onFailure(message: String)
        }
    }
}