package com.example.ddh.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddh.R
import com.example.ddh.data.dto.PartyData

class HomePicturesRecyclerViewAdapter(
    parties: ArrayList<PartyData.Party>
) : RecyclerView.Adapter<HomePicturesRecyclerViewAdapter.HomePictureViewHolder>() {

    private lateinit var view: View
    private var homePictureArrayList = parties

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePictureViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_picture, parent, false)
        HomePictureViewHolder(view)

        return HomePictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePictureViewHolder, position: Int) {
        holder.bindSliderImage(homePictureArrayList[position])
    }

    override fun getItemCount(): Int {
        return homePictureArrayList.size
    }

    inner class HomePictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivHomePicture: ImageView = itemView.findViewById(R.id.iv_home_picture)
        private val tvHomeMountainName: TextView = itemView.findViewById(R.id.tv_home_viewpager_mountain_name)
        private val tvHomePlaceToMeet: TextView = itemView.findViewById(R.id.tv_home_viewpager_place_to_meet)

        fun bindSliderImage(party: PartyData.Party) {
//            Glide.with(view.context)
//                .load(party.)
//                .placeholder(R.drawable.icon_camera)
//                .into(ivHomePicture)
            ivHomePicture.setImageResource(R.drawable.thumnail_mountain_2)
            tvHomeMountainName.text = party.mountain
            tvHomePlaceToMeet.text = party.place
        }
    }


}