package xyz.czanik.indoorway_countries.di.single_country

import dagger.Component
import xyz.czanik.indoorway_countries.single_country.SingleCountryActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [SingleCountryModule::class])
interface SingleCountryComponent {
    fun inject(activity: SingleCountryActivity)
}