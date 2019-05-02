package xyz.czanik.indoorway_countries.di

import dagger.Component
import xyz.czanik.indoorway_countries.countries.CountriesActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [CountriesModule::class])
interface CountriesComponent {
    fun inject(activity: CountriesActivity)
}