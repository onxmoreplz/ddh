package com.example.ddh.upload

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentFirstUploadBinding
import com.example.ddh.upload.UploadActivity.Companion.uploadContentHashMap
import com.jakewharton.rxbinding2.widget.RxTextView

class FirstUploadFragment : UtilityBase.BaseFragment<FragmentFirstUploadBinding>(
    R.layout.fragment_first_upload
), View.OnClickListener {

    private var imageUrl: Uri? = null
    private val REQUEST_CODE_GALLARY = 0
    private var isPhotoUploaded = false

    override fun FragmentFirstUploadBinding.onCreateView() {

    }

    override fun FragmentFirstUploadBinding.onViewCreated() {
        setProgressBar()
        setPhotoLayout()
        setRxBinding()
    }


    private fun setRxBinding() {
        io.reactivex.Observable.combineLatest(
            RxTextView.textChanges(binding.etTitle),
            RxTextView.textChanges(binding.etDescription),
            { rxTitle, rxDescription->
                if (checkTitle(rxTitle) && checkDescription(rxDescription) && isPhotoUploaded) {
                    (activity as UploadActivity).SetNextButtonAble()
                }

            }
        ).subscribe()
    }

    private fun checkDescription(s: CharSequence?): Boolean {
        return s!!.length > 3
    }

    private fun checkTitle(s: CharSequence?): Boolean {
        binding.tvTitleLength.text = "(${s!!.length}/20)"
        return s!!.length > 1
    }

    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 10
    }

    private fun setPhotoLayout() {
        binding.llAddPhoto.setOnClickListener(this)
        binding.ivAddPhoto.setOnClickListener(this)
        binding.ivChangePhoto.setOnClickListener(this)
    }

    fun saveHashMapParty() {
//        uploadContentHashMap["thumbnail"] = imageUrl.toString() //null값 유의
        uploadContentHashMap["title"] = binding.etTitle.text.toString()
        uploadContentHashMap["description"] = binding.etDescription.text.toString()
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
                imageUrl = data?.data
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
                isPhotoUploaded = true
                UploadActivity.progressBar.progress = 30
                binding.ivAddPhoto.setImageBitmap(null)
                binding.ivChangePhoto.visibility = View.VISIBLE
                binding.ivChangePhoto.isEnabled = true


                // 텍스트(타이틀, 설명) 작성 후 사진을 등록한 경우 처리 코드
                if (binding.etTitle.text.length > 1 && binding.etDescription.text.length > 3) {
                    (activity as UploadActivity).SetNextButtonAble()
                }
            } else {
                //Something wrong
            }
        }
    }

}