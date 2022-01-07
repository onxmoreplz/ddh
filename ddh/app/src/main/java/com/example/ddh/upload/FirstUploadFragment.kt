package com.example.ddh.upload

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentFirstUploadBinding

class FirstUploadFragment : UtilityBase.BaseFragment<FragmentFirstUploadBinding>(
    R.layout.fragment_first_upload
), View.OnClickListener {

    private val REQUEST_CODE_GALLARY = 0

    override fun FragmentFirstUploadBinding.onCreateView() {

    }

    override fun FragmentFirstUploadBinding.onViewCreated() {
        setProgressBar()
        setPhotoLayout()
    }

    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 30
    }

    private fun setPhotoLayout() {
        binding.ivAddPhoto.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.ivAddPhoto, binding.llAddPhoto, binding.ivChangePhoto -> {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.setType("image/*")
                startActivityForResult(intent, REQUEST_CODE_GALLARY)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLARY) {
            if (resultCode == Activity.RESULT_OK) {
                var imageUrl = data?.data
                try {
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.icon_camera)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onLoadCleared(placeholder: Drawable?) {}
                            override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                                val layout = binding.llAddPhoto
                                layout.background = resource
                            }
                        })
                }catch (e: Exception) {
//                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
                binding.ivAddPhoto.setImageBitmap(null)
                binding.ivChangePhoto.visibility = View.VISIBLE
            } else {
                //Something wrong
            }
        }
    }

}