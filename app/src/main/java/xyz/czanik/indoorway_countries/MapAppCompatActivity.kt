package xyz.czanik.indoorway_countries

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_single_country.*

/** Abstrakcyjna klasa mająca zwiększyć czytelność kodu klasy jej rozszerzającej
 * poprzez wcześniejszą implementację metod związanych z cyklem życia mapView*/
abstract class MapAppCompatActivity : MessageAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_country)
        mapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}