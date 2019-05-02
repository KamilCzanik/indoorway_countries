package xyz.czanik.indoorway_countries.di.countries

import dagger.Component
import xyz.czanik.indoorway_countries.countries.CountriesActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [CountriesModule::class])
interface CountriesComponent {
    fun inject(activity: CountriesActivity)
}