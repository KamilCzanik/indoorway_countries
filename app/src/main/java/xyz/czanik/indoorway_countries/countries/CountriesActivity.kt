package xyz.czanik.indoorway_countries.countries

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import kotlinx.android.synthetic.main.activity_countries.*
import xyz.czanik.indoorway_countries.MessageAppCompatActivity
import xyz.czanik.indoorway_countries.R
import xyz.czanik.indoorway_countries.di.countries.CountriesModule
import xyz.czanik.indoorway_countries.di.countries.DaggerCountriesComponent
import xyz.czanik.indoorway_countries.single_country.SingleCountryActivity
import javax.inject.Inject


class CountriesActivity : MessageAppCompatActivity(), CountriesMVP.View {

    @Inject override lateinit var presenter: CountriesMVP.Presenter

    override var recyclerAdapter: CountriesRecyclerViewAdapter
        get() = countriesRecyclerView.adapter as CountriesRecyclerViewAdapter
        set(adapter) { countriesRecyclerView.adapter = adapter}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)

        DaggerCountriesComponent.builder()
            .countriesModule(CountriesModule(this))
            .build().inject(this)

        configureRecycler()
        presenter.prepareView()
    }

    private fun configureRecycler() {
        countriesRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        countriesRecyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_countries_menu,menu)
        val myActionMenuItem = menu?.findItem(R.id.action_search_countries)
        val searchView = myActionMenuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(userInput: String): Boolean {
                presenter.onSearch(userInput)
                return true
            }

            override fun onQueryTextChange(userInput: String): Boolean {
                presenter.onSearch(userInput)
                return true
            }
        })
        return true
    }

    override fun startSingleCountryActivityFor(name: String) {
        startActivity(SingleCountryActivity.createIntent(this,name))
    }
}
