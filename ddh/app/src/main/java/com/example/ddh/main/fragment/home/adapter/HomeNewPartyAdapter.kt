package com.example.ddh.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ddh.R
import com.example.ddh.data.dto.PartyData

class HomeNewPartyAdapter(
    parties: ArrayList<PartyData.Party>
): RecyclerView.Adapter<HomeNewPartyAdapter.HomeNewPartyViewHolder>() {

    private lateinit var view: View
    private var newParties = parties

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewPartyViewHolder {
        view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_new_party,
                parent,
                false
            )

        return HomeNewPartyViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeNewPartyViewHolder, position: Int) {
        val party = newParties[position]
        holder.bind(party)

    }

    override fun getItemCount(): Int {
        return newParties.size
    }

    fun updateNewParty(parties: ArrayList<PartyData.Party>) {
        newParties.clear()
        newParties.addAll(parties)
    }

    inner class HomeNewPartyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvNewPartyTitle = itemView.findViewById<TextView>(R.id.tv_new_party_title)
        private val tvNewPartyPlace = itemView.findViewById<TextView>(R.id.tv_new_party_place)
        private val tvNewPartyDate = itemView.findViewById<TextView>(R.id.tv_new_party_date)
        private val tvNewPartylikesCount = itemView.findViewById<TextView>(R.id.tv_new_party_likes_count)
        private val tvNewPartyCommentCount = itemView.findViewById<TextView>(R.id.tv_new_party_comment_count)

        private val rvNewPartyParticipant = itemView.findViewById<RecyclerView>(R.id.rv_new_party_participant)

        fun bind(party: PartyData.Party) {
            tvNewPartyTitle.text = party.title
            tvNewPartyPlace.text = party.place
            tvNewPartyDate.text = party.departureAt
            tvNewPartylikesCount.text = party.minimum
            tvNewPartyCommentCount.text = party.maximum.toString()

            rvNewPartyParticipant.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            rvNewPartyParticipant.setHasFixedSize(true)
            rvNewPartyParticipant.adapter = PartyParticipantAdapter(party.participants!!)


        }
    }
}