package com.example.ddh.main.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ddh.R

class HomePicturesViewPagerAdapter(
    ctx: Context,
    imageUrls: java.util.ArrayList<String>
) : RecyclerView.Adapter<HomePicturesViewPagerAdapter.ViewPagerViewHolder>() {

    private val ctx = ctx
    private lateinit var view: View
    private var homePictureUrlArrayList = imageUrls

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePicturesViewPagerAdapter.ViewPagerViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_picture, parent, false)
        ViewPagerViewHolder(view)

        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bindSliderImage(homePictureUrlArrayList[position])
    }

    override fun getItemCount(): Int {
        return homePictureUrlArrayList.size
    }

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivHomePicture: ImageView = itemView.findViewById(R.id.iv_home_picture)

        fun bindSliderImage(imageUrl: String) {
            Glide.with(ctx)
                .load(imageUrl)
                .placeholder(R.drawable.ic_home)
                .into(ivHomePicture)
        }
    }


}