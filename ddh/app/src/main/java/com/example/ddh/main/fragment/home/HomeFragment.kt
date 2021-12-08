package com.example.ddh.main.fragment.home

import android.icu.util.IndianCalendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ddh.R
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {

    private lateinit var v: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        setViewPager()
        return v
    }

    private fun setViewPager() {
        val arrayList = arrayListOf<String>(
            "https://occ-0-1723-1722.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABX8elBVb9H-mD41yusrFQU1Xk74Czvwxtv7eiwINBiSFJ7OhCOHJ15zdQenwFIcwg0THBdqKMpJdBPVdKPsA0mfHos8.jpg?r=878",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR981xPJeg67gdRecTwM27UQq3DeobJdP2ERw&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZvWOF2nTMtMYemS0Heu7D1WqnovJlYmQnW_5iuBMa8Pnyd8wS53Niom9XkQpYkSjdymo&usqp=CAU"
        )

        val vpHomePictures: ViewPager2 = v.findViewById(R.id.vp_home_picture)
        val indicator: CircleIndicator3 = v.findViewById(R.id.vp_indicator)
        vpHomePictures.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpHomePictures.adapter = HomePicturesViewPagerAdapter(v.context, arrayList)

        // 슬라이드할 경우 실행할 이벤트
        vpHomePictures.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })

        indicator.setViewPager(vpHomePictures)

    }
}