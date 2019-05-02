package xyz.czanik.indoorway_countries.countries

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.country_item.view.*
import xyz.czanik.indoorway_countries.R

class CountriesRecyclerViewAdapter(
    private val activity: CountriesActivity,
    private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>() {

    var countries = listOf<CountryItem>()
    set(countries) {
        field = countries
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(itemPos: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.country_item,viewGroup,false))

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(viewholder: ViewHolder, pos: Int) {
        val view = viewholder.itemView
        view.countryItemName.text = countries[pos].name
        GlideToVectorYou.justLoadImage(activity, Uri.parse(countries[pos].flag), view.countryItemFlag)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init { itemView.setOnClickListener { onItemClickListener.onItemClick(adapterPosition) } }
    }
}