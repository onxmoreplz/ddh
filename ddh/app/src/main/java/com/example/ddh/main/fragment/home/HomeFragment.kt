package com.example.ddh.main.fragment.home

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.databinding.FragmentHomeBinding
import com.example.ddh.main.fragment.home.adapter.HomeNewPartyAdapter
import com.example.ddh.main.fragment.home.adapter.HomePicturesRecyclerViewAdapter
import com.example.ddh.upload.UploadActivity
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : UtilityBase.BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home
) {
    private lateinit var v: View
    private var arrayListNewParty = ArrayList<PartyData.Party>()

    override fun FragmentHomeBinding.onCreateView() {

        v = binding.root

        val tmpUser1 = SignUpUserData.User(
            "1", "1", "1", "함길녀", "1", "1", "1", Date(2022, 1, 31), "1",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.postplanner.com%2Ffacebook-profile-picture-more-important-than-cover-photo-for-pages%2F&psig=AOvVaw0d4JKOPLSHy_0ebzq25ZrU&ust=1641456041702000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCODTuLaSmvUCFQAAAAAdAAAAABAD"
        )
        val tmpUser2 = SignUpUserData.User(
            "1", "1", "1", "패닝", "1", "1", "1", Date(2022, 1, 31), "1",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.postplanner.com%2Ffacebook-profile-picture-more-important-than-cover-photo-for-pages%2F&psig=AOvVaw0d4JKOPLSHy_0ebzq25ZrU&ust=1641456041702000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCODTuLaSmvUCFQAAAAAdAAAAABAD"
        )
        arrayListNewParty.add(
            PartyData.Party(
                7, "대화하면서 즐거운 산행 함께 떠나요!", "--", "도봉산", "강남역", "남자만", 20000, "비행기값", 5, "123", "1. 3.(월) 오전 11시",
                arrayListOf<SignUpUserData.User>(tmpUser1, tmpUser1, tmpUser1)
            )
        )
        arrayListNewParty.add(
            PartyData.Party(
                7, "대화하면서 즐거운 산행 함께 떠나요!", "--", "도봉산", "강남역 11번 출구", "남자만", 20000, "비행기값", 5, "123", "1. 3.(월) 오전 11시",
                arrayListOf<SignUpUserData.User>(tmpUser1, tmpUser2, tmpUser2)
            )
        )
        arrayListNewParty.add(
            PartyData.Party(
                7, "대화하면서 즐거운 산행 함께 떠나요!", "--", "북한산", "강남역", "남자만", 20000, "비행기값", 5, "123", "1. 3.(월) 오전 11시",
                arrayListOf<SignUpUserData.User>(tmpUser1, tmpUser1, tmpUser1)
            )
        )
        arrayListNewParty.add(
            PartyData.Party(
                7, "대화하면서 즐거운 산행 함께 떠나요!", "--", "에베레스트", "강남역", "남자만", 20000, "비행기값", 5, "123", "1. 3.(월) 오전 11시",
                arrayListOf<SignUpUserData.User>(tmpUser1, tmpUser1, tmpUser1)
            )
        )

        setRecyclerview()
        setFloatingButton()
    }

    private fun setFloatingButton() {
        binding.fabCreateParty.setOnClickListener {
            startActivity(Intent(v.context, UploadActivity::class.java))
        }
    }


    override fun FragmentHomeBinding.onViewCreated() {

    }

    private fun setRecyclerview() {

        val rvHomePictures: RecyclerView = binding.vpHomePicture
        var vpNewParty: ViewPager2 = binding.vpNewParty
        var vpDeadlineParty: ViewPager2 = binding.vpDeadlineParty
        var vpHotParty: ViewPager2 = binding.vpHotParty

        rvHomePictures.layoutManager = LinearLayoutManager(v.context, LinearLayoutManager.HORIZONTAL, false)
        vpNewParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpDeadlineParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpHotParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        rvHomePictures.adapter = HomePicturesRecyclerViewAdapter(arrayListNewParty)
        vpNewParty.adapter = HomeNewPartyAdapter(arrayListNewParty)
        vpDeadlineParty.adapter = HomeNewPartyAdapter(arrayListNewParty)
        vpHotParty.adapter = HomeNewPartyAdapter(arrayListNewParty)

        binding.vpNewParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER //스크롤 효과 없애기
        binding.vpDeadlineParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpHotParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER


//        vpNewParty.addItemDecoration(object: RecyclerView.ItemDecoration() {
//            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                outRect.right = 40
//            }
//        })
//
//        val pageTranslationX = 20 + 40
//        vpNewParty.offscreenPageLimit = 1
//        vpNewParty.setPageTransformer { page, position -> page.translationX = -pageTranslationX * ( position) }
//

    }
}