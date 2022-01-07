package com.example.ddh.main.fragment.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ddh.R
import com.example.ddh.data.dto.SignUpUserData
import de.hdodenhof.circleimageview.CircleImageView

class PartyParticipantAdapter (
    participants: ArrayList<SignUpUserData.User>
): RecyclerView.Adapter<PartyParticipantAdapter.PartyParticipantViewHolder>() {

    private lateinit var view: View
    private var participants = participants

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyParticipantAdapter.PartyParticipantViewHolder {
        view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_party_participant_profile_pic,
            parent,
            false
        )

        return PartyParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PartyParticipantViewHolder, position: Int) {
        val user = participants[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return participants.size
    }

    inner class PartyParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val civProfilePic = itemView.findViewById<CircleImageView>(R.id.civ_participant_profile_pic)
        private val tvNickname = itemView.findViewById<TextView>(R.id.tv_participant_nickname)

        fun bind(user: SignUpUserData.User) {
            tvNickname.text = user.nickName
            try {
                Glide.with(view)
                    .load(user.profilePicUrl)
                    .placeholder(R.drawable.icon_default_profile_pic)
                    .into(civProfilePic)
            } catch (e: Exception) {
                Log.e("ParticipantViewHolder", "Glide Error : $e")
            }
        }
    }



}