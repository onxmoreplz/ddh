package com.example.ddh.upload

import android.view.View
import com.example.ddh.R
import com.example.ddh.base.UtilityBase
import com.example.ddh.databinding.FragmentSecondUploadBinding

class SecondUploadFragment : UtilityBase.BaseFragment<FragmentSecondUploadBinding>(
    R.layout.fragment_second_upload
), View.OnClickListener {

    override fun FragmentSecondUploadBinding.onCreateView() {

    }

    override fun FragmentSecondUploadBinding.onViewCreated() {
        setProgressBar()
        setButton()
    }

    private fun setButton() {

    }

    private fun setProgressBar() {
        UploadActivity.progressBar.progress = 60
    }

    override fun onClick(v: View?) {

    }

}