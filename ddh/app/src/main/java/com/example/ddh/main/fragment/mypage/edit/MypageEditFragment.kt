package com.example.ddh.main.fragment.mypage.edit

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentMypageEditBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.main.fragment.mypage.MypageFragment


class MypageEditFragment : UtilityBase.BaseFragment<FragmentMypageEditBinding>(
    R.layout.fragment_mypage_edit
), View.OnClickListener {

    private val mypageFragment by lazy { MypageFragment() }

    private val REQUEST_CODE_GALLARY = 0

    override fun FragmentMypageEditBinding.onCreateView() {

    }

    override fun FragmentMypageEditBinding.onViewCreated() {
        setTextViewAndEditText()
        setOnClickListener()
    }

    private fun setTextViewAndEditText() {
        binding.edtUserName.text = Editable.Factory.getInstance().newEditable(App.sharedPrefs.getUserName())
        binding.tvUserEmailMyPageEdit.text = App.sharedPrefs.getUserEmail()
        binding.etPhone1.text = Editable.Factory.getInstance().newEditable(App.sharedPrefs.getUserTel()!!.substring(0, 3))
        binding.etPhone2.text = Editable.Factory.getInstance().newEditable(App.sharedPrefs.getUserTel()!!.substring(3, 7))
        binding.etPhone3.text = Editable.Factory.getInstance().newEditable(App.sharedPrefs.getUserTel()!!.substring(App.sharedPrefs.getUserTel()!!.length - 4, App.sharedPrefs.getUserTel()!!.length))
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
                        .placeholder(R.drawable.icon_camera)
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