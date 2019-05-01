package xyz.czanik.indoorway_countries.countries

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_countries.*
import xyz.czanik.indoorway_countries.R
import xyz.czanik.indoorway_countries.single_country.SingleCountryActivity
import android.support.v4.view.MenuItemCompat.getActionView
import android.support.v7.widget.SearchView
import javax.inject.Inject


class CountriesActivity : AppCompatActivity(), CountriesMVP.View {

    @Inject override lateinit var presenter: CountriesMVP.Presenter

    override var recyclerAdapter: CountriesRecyclerViewAdapter
        get() = countriesRecyclerView.adapter as CountriesRecyclerViewAdapter
        set(adapter) { countriesRecyclerView.adapter = adapter}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
    }

    override fun onResume() {
        super.onResume()
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

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext,message,LENGTH_LONG).show()
    }

    override fun startSingleCountryActivityFor(name: String) {
        startActivity(SingleCountryActivity.createIntent(this,name))
    }
}
