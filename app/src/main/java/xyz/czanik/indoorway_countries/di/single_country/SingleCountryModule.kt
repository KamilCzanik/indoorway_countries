package xyz.czanik.indoorway_countries.di.single_country

import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.czanik.indoorway_countries.single_country.SingleCountryActivity
import xyz.czanik.indoorway_countries.single_country.SingleCountryMVP
import xyz.czanik.indoorway_countries.single_country.SingleCountryModel
import xyz.czanik.indoorway_countries.single_country.SingleCountryPresenter
import javax.inject.Singleton

@Module
class SingleCountryModule(private val activity: SingleCountryActivity) {

    @Provides
    fun providesContext() : Context = activity

    @Provides
    fun providesView() : SingleCountryMVP.View = activity

    @Singleton
    @Provides
    fun providesPresenter(presenter: SingleCountryPresenter) : SingleCountryMVP.Presenter = presenter

    @Singleton
    @Provides
    fun providesModel(model: SingleCountryModel) : SingleCountryMVP.Model = model
}