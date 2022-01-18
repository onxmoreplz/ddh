package com.example.ddh.main.fragment.mypage

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMypageBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.main.fragment.mypage.edit.MypageEditFragment

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
        binding.llNotice.setOnClickListener(this)
        binding.tvNotice.setOnClickListener(this)
        binding.llCustomerCenter.setOnClickListener(this)
        binding.tvCustomerCenter.setOnClickListener(this)
        binding.llBanned.setOnClickListener(this)
        binding.tvBanned.setOnClickListener(this)
        binding.llAgreementPolicy.setOnClickListener(this)
        binding.tvAgreementPolicy.setOnClickListener(this)
        binding.llMypageEdit.setOnClickListener(this)
        binding.tvMapageEdit.setOnClickListener(this)
        binding.llLogout.setOnClickListener(this)
        binding.tvLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.llNotice, binding.tvNotice -> {

            }
            binding.llCustomerCenter, binding.tvCustomerCenter -> {

            }
            binding.llBanned, binding.tvBanned -> {

            }
            binding.llAgreementPolicy, binding.tvAgreementPolicy -> {

            }
            binding.llMypageEdit, binding.tvMapageEdit -> {
                (activity as MainActivity).replaceFragmentAddToBackStack(mypageEditFragment)
            }
            binding.llLogout, binding.tvLogout -> {
                showLogoutDialogue()
            }
        }
    }

    private fun showLogoutDialogue() {
        var builder = AlertDialog.Builder(context)
        builder.setTitle("로그아웃")
        builder.setMessage("지금 로그아웃하시겠습니까?")
        var listener = DialogInterface.OnClickListener { _, p1 ->
            when (p1) {
                DialogInterface.BUTTON_NEGATIVE -> { // 아니오(로그아웃 안함)

                }
                DialogInterface.BUTTON_POSITIVE -> { // 예(로그아웃)

                }
            }
        }
        builder.setPositiveButton("사용하기", listener)
        builder.show()
    }
}