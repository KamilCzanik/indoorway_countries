package xyz.czanik.indoorway_countries

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

abstract class MessageAppCompatActivity : AppCompatActivity() {

    fun showMessage(message: String) {
        Toast.makeText(applicationContext,message,LENGTH_LONG).show()
    }
}