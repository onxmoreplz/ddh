package com.example.ddh.main.fragment.mypage

import android.content.Intent
import android.view.View
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMypageBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.signup.SignUpEmailActivity

class MypageFragment : UtilityBase.BaseFragment<FragmentMypageBinding>(
    R.layout.fragment_mypage
), View.OnClickListener {
    private val mypageEditFragment by lazy { MypageEditFragment() }

    override fun FragmentMypageBinding.onCreateView() {

    }

    override fun FragmentMypageBinding.onViewCreated() {
        setTextview()
        setOnClickListener()
    }

    private fun setTextview() {
        binding.tvUserNicknameMyPage.text = App.sharedPrefs.getUserNickname()
        binding.tvUserEmailMyPage.text = App.sharedPrefs.getUserEmail()
    }

    private fun setOnClickListener() {
        binding.btnMyPageEdit.setOnClickListener(this)
        binding.tvUserNicknameMyPage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnMyPageEdit -> {
                (activity as MainActivity).replaceFragmentAddToBackStack(mypageEditFragment)
            }
            binding.tvUserNicknameMyPage -> {
                (activity as MainActivity).replaceFragmentAddToBackStack(mypageEditFragment)
            }
        }
    }
}