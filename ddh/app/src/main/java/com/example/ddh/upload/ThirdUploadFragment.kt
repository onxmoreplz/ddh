package com.example.ddh.upload

import android.graphics.Color
import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentThirdUploadBinding

class ThirdUploadFragment : UtilityBase.BaseFragment<FragmentThirdUploadBinding>(
    R.layout.fragment_third_upload
), View.OnClickListener {

    override fun FragmentThirdUploadBinding.onCreateView() {

    }

    override fun FragmentThirdUploadBinding.onViewCreated() {
        setProgressBar()
        setButton()
    }

    private fun setButton() {
        binding.btnFree.setOnClickListener(this)
        binding.btnNotFree.setOnClickListener(this)

        binding.btnFree.setBackgroundResource(R.drawable.button_fee_clicked)
        binding.btnFree.setTextColor(Color.WHITE)
        binding.btnNotFree.setBackgroundResource(R.drawable.edittext_round_id_password)
        binding.btnNotFree.setTextColor(Color.rgb(188, 188, 188))
    }

    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 90
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnFree -> {
                btnFreeClickedListener()
            }
            binding.btnNotFree -> {
                btnNotFreeClickedListener()
            }
        }


    }

    fun btnFreeClickedListener() {
        binding.btnFree.setBackgroundResource(R.drawable.button_fee_clicked)
        binding.btnFree.setTextColor(Color.WHITE)
        binding.btnNotFree.setBackgroundResource(R.drawable.edittext_round_id_password)
        binding.btnNotFree.setTextColor(Color.rgb(188, 188, 188))


        binding.llNotFree.visibility = View.GONE

    }

    fun btnNotFreeClickedListener() {
        binding.btnFree.setBackgroundResource(R.drawable.edittext_round_id_password)
        binding.btnFree.setTextColor(Color.rgb(188, 188, 188))
        binding.btnNotFree.setBackgroundResource(R.drawable.button_fee_clicked)
        binding.btnNotFree.setTextColor(Color.WHITE)

        binding.llNotFree.visibility = View.VISIBLE
    }

}