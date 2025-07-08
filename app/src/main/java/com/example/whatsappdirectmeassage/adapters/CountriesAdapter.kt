package com.example.whatsappdirectmeassage.adapters

//import com.example.whatsappdirectmeassage.models.countries
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.whatsappdirectmeassage.activities.MyApplication
import com.example.whatsappdirectmeassage.databinding.CountryListItemBinding
import com.example.whatsappdirectmeassage.utils.countries
import com.example.whatsappdirectmeassage.utils.loadSvgImage
import com.l4digital.fastscroll.FastScroller

class CountriesAdapter(
    private val clickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<CountriesAdapter.CountryHolder>(),
    FastScroller.SectionIndexer {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return CountryHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        val country = countries[position]
        val name = "${country.name}, ${country.phone_code}"
        val code = "+${country.phone_code}"


        Log.e(":hfcghnfgh", "onBindViewHolder: ${country.flag_url.replace("ht", "")}")
//        holder.flag.load(country.flag_url)
        holder.countryName.text = name
        holder.isdCode.text = code


//        val request = ImageRequest.Builder(holder.itemView.context)
//            .data(country.flag_url)
//            .crossfade(true)
//            .target(holder.flag)
//            .build()
//
//        MyApplication.getImageLoader().enqueue(request)

        holder.flag.loadSvgImage(country.flag_url)


    }

    override fun getItemCount(): Int = countries.size

    override fun getSectionText(position: Int): CharSequence = countries[position].phone_code

    class CountryHolder(binding: CountryListItemBinding, clickListener: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        val countryName: TextView = binding.countryName
        val isdCode: TextView = binding.countryCode
        val flag: ImageView = binding.flag

        init {
            binding.root.setOnClickListener { clickListener.invoke(adapterPosition) }
        }
    }
}