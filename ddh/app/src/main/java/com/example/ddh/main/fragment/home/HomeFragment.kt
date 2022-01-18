package com.example.ddh.main.fragment.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ddh.R
import com.example.ddh.R.layout
import com.example.ddh.base.UtilityBase
import com.example.ddh.data.dto.PartyData
import com.example.ddh.data.dto.SignUpUserData
import com.example.ddh.databinding.FragmentHomeBinding
import com.example.ddh.main.fragment.home.adapter.HomeNewPartyAdapter
import com.example.ddh.main.fragment.home.adapter.HomePicturesRecyclerViewAdapter
import com.example.ddh.upload.UploadActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class HomeFragment : UtilityBase.BaseFragment<FragmentHomeBinding>(
    layout.fragment_home
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

        setViewPager()
        setFloatingButton()
    }

    private fun setFloatingButton() {
        binding.fabCreateParty.setOnClickListener {
            startActivity(Intent(v.context, UploadActivity::class.java))
        }
    }


    override fun FragmentHomeBinding.onViewCreated() {

    }

    private fun setViewPager() {

        val vpHomePictures: ViewPager2 = binding.vpHomePicture
        var vpNewParty: ViewPager2 = binding.vpNewParty
        var vpDeadlineParty: ViewPager2 = binding.vpDeadlineParty
        var vpHotParty: ViewPager2 = binding.vpHotParty

        vpHomePictures.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpNewParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpDeadlineParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        vpHotParty.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        vpHomePictures.adapter = HomePicturesRecyclerViewAdapter(arrayListNewParty)
        vpNewParty.adapter = HomeNewPartyAdapter(arrayListNewParty)
        vpDeadlineParty.adapter = HomeNewPartyAdapter(arrayListNewParty)
        vpHotParty.adapter = HomeNewPartyAdapter(arrayListNewParty)

        binding.vpHomePicture.offscreenPageLimit = 5
        binding.vpNewParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpDeadlineParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.vpHotParty.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        /** 뷰페이저 줌인 기능 */
        // You need to retain one page on each side so that the next and previous items are visible
        vpHomePictures.offscreenPageLimit = 1

        // Add a PageTransformer that translates the next and previous items horizontally
        // towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // 좌, 우에 보여지는 아이템 비율
            page.scaleY = 1 - (0.23f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        vpHomePictures.setPageTransformer(pageTransformer)

        // The ItemDecoration gives the current (centered) item horizontal margin so that
        // it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = context?.let {
            HorizontalMarginItemDecoration(
                it,
                R.dimen.viewpager_current_item_horizontal_margin
            )
        }
        vpHomePictures.addItemDecoration(itemDecoration!!)


        /* val pageMargin = resources.getDimensionPixelOffset(dimen.pageMargin).toFloat()
         val pageOffset = resources.getDimensionPixelOffset(dimen.offset).toFloat()

         binding.vpHomePicture.setPageTransformer { page, position ->
             val myOffset: Float = position * -(2 * pageOffset + pageMargin)
             when {
                 position < -1 -> {
                     page.translationX = -myOffset
                 }
                 position <= 1 -> {
                     // (0,1]
                     // Fade the page out.
                     val scaleFactor = 0.7f.coerceAtLeast(1 - abs(position - 0.14285715f))
                     page.translationX = myOffset
                     page.scaleY = scaleFactor
                     page.alpha = scaleFactor
                 }
                 else -> {// (1,+Infinity]
                     // This page is way off-screen to the right.
                     page.alpha = 0F
                     page.translationX = myOffset
                 }
             }
         }*/


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

    /**
     * Adds margin to the left and right sides of the RecyclerView item.
     * Adapted from https://stackoverflow.com/a/27664023/4034572
     * @param horizontalMarginInDp the margin resource, in dp.
     */
    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int = context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }
}