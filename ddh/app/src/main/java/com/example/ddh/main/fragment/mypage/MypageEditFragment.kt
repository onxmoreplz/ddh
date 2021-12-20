package com.example.ddh.main.fragment.mypage

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
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

    val REQUEST_CODE_GALLARY = 0

    override fun FragmentMypageEditBinding.onCreateView() {

    }

    override fun FragmentMypageEditBinding.onViewCreated() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnEditName.setOnClickListener(this)
        binding.btnEditEmail.setOnClickListener(this)
        binding.btnEditPassword.setOnClickListener(this)
        binding.btnEditPhone.setOnClickListener(this)
        binding.btnEditBirth.setOnClickListener(this)
        binding.civProfilePic.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnEditName -> {
                (activity as MainActivity).replaceFragment(editNameFragment)
            }
            binding.btnEditEmail -> {
                (activity as MainActivity).replaceFragment(editEmailFragment)
            }
            binding.btnEditPassword -> {
                (activity as MainActivity).replaceFragment(editPassWordFragment)
            }
            binding.btnEditPhone -> {
                (activity as MainActivity).replaceFragment(editPhoneFragment)
            }
            binding.btnEditBirth -> {
                (activity as MainActivity).replaceFragment(editBirthFragment)
            }
            binding.civProfilePic, binding.civEditProfilePictureCamera -> {
                loadImage()
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
                }catch (e: Exception) {
//                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            } else {
                //Something wrong
            }
        }
    }
}