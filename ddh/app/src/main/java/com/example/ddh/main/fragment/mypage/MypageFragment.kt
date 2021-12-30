package com.example.ddh.main.fragment.mypage

import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMypageBinding
import com.example.ddh.main.MainActivity

class MypageFragment : UtilityBase.BaseFragment<FragmentMypageBinding>(
    R.layout.fragment_mypage
), View.OnClickListener {
    private val mypageEditFragment by lazy { MypageEditFragment() }

    override fun FragmentMypageBinding.onCreateView() {

    }

    override fun FragmentMypageBinding.onViewCreated() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnMyPageEdit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnMyPageEdit -> {
                (activity as MainActivity).replaceFragmentAddToBackStack(mypageEditFragment)
            }
        }
    }
}