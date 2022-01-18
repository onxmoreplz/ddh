package com.example.ddh.upload

import android.content.Intent
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.data.repository.RepositoryImpl
import com.example.ddh.databinding.FragmentPreviewBinding
import com.example.ddh.main.MainActivity
import com.example.ddh.upload.UploadActivity.Companion.uploadContentHashMap

class PreviewFragment : UtilityBase.BaseFragment<FragmentPreviewBinding>(
    R.layout.fragment_preview
), View.OnClickListener {

    private val repository = RepositoryImpl() // 의존성 주입을 위한 Repository 객체 생성

    override fun FragmentPreviewBinding.onCreateView() {

    }

    override fun FragmentPreviewBinding.onViewCreated() {
        setHashMapUpload()
        setButton()
    }

    private fun setButton() {
        binding.btnUpload.setOnClickListener(this)
    }

    private fun setHashMapUpload() {
        setPhoto()
        binding.tvTitle.text = UploadActivity.uploadContentHashMap["title"].toString()
        binding.etDescription.text = Editable.Factory.getInstance().newEditable(UploadActivity.uploadContentHashMap["description"].toString())
        binding.tvMountain.text = UploadActivity.uploadContentHashMap["mountain"].toString()
        binding.tvPlaceToMeet.text = UploadActivity.uploadContentHashMap["place"].toString()
        setGender()
        setMaxAndMin()
        binding.tvDate.text = UploadActivity.uploadContentHashMap["departureAt"].toString().substring(0,10)
        binding.tvTime.text = UploadActivity.uploadContentHashMap["departureAt"].toString().substring(10,UploadActivity.uploadContentHashMap["departureAt"].toString().length)
        setCost()
    }

    private fun setCost() {
        if (UploadActivity.uploadContentHashMap["cost"] != 0) {
            binding.tvFee.text = numberWithCommas(UploadActivity.uploadContentHashMap["cost"] as Int)
            binding.etFeeDescription.text = Editable.Factory.getInstance().newEditable(UploadActivity.uploadContentHashMap["costDescription"].toString())
            binding.etFeeDescription.visibility = View.VISIBLE
        } else {
            binding.tvFee.text = UploadActivity.uploadContentHashMap["cost"].toString()
        }
    }

    private fun numberWithCommas(x: Int): String {
        return x.toString().replace("\\B(?=(\\d{3})+(?!\\d))", ",")
    }

    private fun setGender() {
        when(UploadActivity.uploadContentHashMap["gender"].toString()) {
            "normal" -> {
                binding.tvGender.text = "성별 상관없음"
            }
            "male" -> {
                binding.tvGender.text = "여자"
            }
            "female" -> {
                binding.tvGender.text = "남자"
            }
        }
    }

    private fun setMaxAndMin() {
        var max: String = UploadActivity.uploadContentHashMap["maximum"].toString()
        var min: String = UploadActivity.uploadContentHashMap["minimum"].toString()
        var resultMinMax = "최소 $min 명 ~ 최대 $max 명"
        binding.tvMinMax.text = resultMinMax
    }

    private fun setPhoto() {
        try {
            Glide.with(this)
                .load(UploadActivity.uploadContentHashMap["thumbnail"])
                .placeholder(R.drawable.icon_camera)
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                        val layout = binding.llPhoto
                        layout.background = resource
                    }
                })
        }catch (e: Exception) {
//                    Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnUpload -> {

                repository.postUploadParty(
                    uploadContentHashMap,
                    success = {
                        it.run {
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
//                            finish()
                            Toast.makeText(activity, "모임생성이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                        Log.d("Button Sign Up", "Success to SignUp")
                    },
                    fail = {
                        Toast.makeText(activity, "${it.message}", Toast.LENGTH_SHORT).show()
                        Log.d("Post Upload Party", it.message.toString())
                    }

                )
            }
        }
    }

}