package xyz.czanik.indoorway_countries.countries

class CountriesRecyclerViewAdapter {

    var countries = listOf<CountryItem>()

    interface OnItemClickListener {
        fun onItemClick(itemPos: Int)
    }
}