package com.example.ddh.main.fragment.home

import androidx.viewpager2.widget.ViewPager2
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : UtilityBase.BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {

    override fun FragmentHomeBinding.onCreateView() {
        setViewPager()
    }

    override fun FragmentHomeBinding.onViewCreated() {
    }

    private fun setViewPager() {
        val arrayList = arrayListOf<String>(
            "https://occ-0-1723-1722.1.nflxso.net/dnm/api/v6/X194eJsgWBDE2aQbaNdmCXGUP-Y/AAAABX8elBVb9H-mD41yusrFQU1Xk74Czvwxtv7eiwINBiSFJ7OhCOHJ15zdQenwFIcwg0THBdqKMpJdBPVdKPsA0mfHos8.jpg?r=878",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR981xPJeg67gdRecTwM27UQq3DeobJdP2ERw&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZvWOF2nTMtMYemS0Heu7D1WqnovJlYmQnW_5iuBMa8Pnyd8wS53Niom9XkQpYkSjdymo&usqp=CAU"
        )

        val vpHomePictures: ViewPager2 = binding.vpHomePicture
        val indicator: CircleIndicator3 = binding.vpIndicator
        vpHomePictures.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpHomePictures.adapter = HomePicturesViewPagerAdapter(arrayList)

        // 슬라이드할 경우 실행할 이벤트
        vpHomePictures.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })

        indicator.setViewPager(vpHomePictures)

    }
}