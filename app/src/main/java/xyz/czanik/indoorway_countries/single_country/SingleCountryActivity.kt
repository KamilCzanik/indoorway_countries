package xyz.czanik.indoorway_countries.single_country

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import xyz.czanik.indoorway_countries.R

class SingleCountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_country)
    }

    companion object {
        const val INTENT_COUNTRY_NAME = "country_name"
        fun createIntent(context: Context,name: String) : Intent {
            return Intent(context, SingleCountryActivity::class.java).apply {
                putExtra(INTENT_COUNTRY_NAME, name)
            }
        }
    }
}
