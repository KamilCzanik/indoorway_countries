package xyz.czanik.indoorway_countries.di.countries

import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.czanik.indoorway_countries.countries.CountriesActivity
import xyz.czanik.indoorway_countries.countries.CountriesMVP
import xyz.czanik.indoorway_countries.countries.CountriesModel
import xyz.czanik.indoorway_countries.countries.CountriesPresenter
import javax.inject.Singleton

@Module
class CountriesModule(private val countriesActivity: CountriesActivity) {

    @Provides
    fun providesView() : CountriesMVP.View = countriesActivity

    @Provides
    fun providesContext() : Context = countriesActivity

    @Singleton
    @Provides
    fun providesPresenter(presenter: CountriesPresenter) : CountriesMVP.Presenter = presenter

    @Singleton
    @Provides
    fun providesModel(model: CountriesModel) : CountriesMVP.Model = model
}