package com.example.ddh.main.fragment.mypage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMypageEditBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.main.fragment.mypage.edit.*


class MypageEditFragment : UtilityBase.BaseFragment<FragmentMypageEditBinding>(
    R.layout.fragment_mypage_edit
), View.OnClickListener {

    private val editNameFragment by lazy { EditNameFragment() }
    private val editEmailFragment by lazy { EditEmailFragment() }
    private val editPassWordFragment by lazy { EditPasswordFragment() }
    private val editPhoneFragment by lazy { EditPhoneFragment() }
    private val editBirthFragment by lazy { EditBirthFragment() }
    private val mypageFragment by lazy { MypageFragment() }

    val REQUEST_CODE_GALLARY = 0

    override fun FragmentMypageEditBinding.onCreateView() {

    }

    override fun FragmentMypageEditBinding.onViewCreated() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.civProfilePic.setOnClickListener(this)
        binding.btnBackToMypage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.civProfilePic, binding.civEditProfilePictureCamera -> {
                loadImage()
            }
            binding.btnBackToMypage -> {
                (activity as MainActivity).replaceFragmentNoBackStack(mypageFragment)
            }
        }
    }

    private fun loadImage() {
        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }

            startActivityForResult(intent, REQUEST_CODE_GALLARY)
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("image/*")
            Log.d("here", "here")
            startActivityForResult(intent, REQUEST_CODE_GALLARY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLARY) {
            if (resultCode == RESULT_OK) {
                var imageUrl = data?.data
                try {
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.icon_home)
                        .into(binding.civProfilePic)
                } catch (e: Exception) {
//                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            } else {
                //Something wrong
            }
        }
    }
}