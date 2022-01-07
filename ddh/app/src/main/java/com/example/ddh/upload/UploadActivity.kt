package com.example.ddh.upload

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.ddh.App
import com.example.ddh.R
import com.example.ddh.databinding.ActivityUploadBinding

class UploadActivity : FragmentActivity(), View.OnClickListener {

    private lateinit var databinding: ActivityUploadBinding
    private var fragmentIndex = 0

    private val firstUploadFragment by lazy { FirstUploadFragment() }
    private val secondUploadFragment by lazy { SecondUploadFragment() }
    private val thirdUploadFragment by lazy { ThirdUploadFragment() }

    companion object {
        var progressBar = ProgressBar(App.instance) // 프로그레스 바
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_upload)

        replaceFragmentAddToBackStack(firstUploadFragment)

        setProgressBar()
        setButton()
    }

    private fun setProgressBar() {
        progressBar = databinding.pbUpload
    }

    private fun setButton() {
        databinding.btnBack.setOnClickListener(this)
        databinding.btnNext.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v) {
            // TODO : 로직 수정 필요(프래그먼트 전환)
            databinding.btnBack -> {
                if (fragmentIndex == 0) {
                    finish()
                } else if (fragmentIndex == 1) {
                    fragmentIndex = 0
                    replaceFragmentAddToBackStack(firstUploadFragment)
                } else if(fragmentIndex == 2) {
                    fragmentIndex = 1
                    replaceFragmentAddToBackStack(secondUploadFragment)
                }
            }
            databinding.btnNext -> {
                if (fragmentIndex == 0) {
                    replaceFragmentAddToBackStack(secondUploadFragment)
                    fragmentIndex = 1
                } else if (fragmentIndex == 1) {
                    replaceFragmentAddToBackStack(thirdUploadFragment)
                    fragmentIndex = 2
                } else if(fragmentIndex == 2) {
                    //
                }
            }
        }
    }


    fun replaceFragmentAddToBackStack(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.fl_container_upload, fragment)
            .addToBackStack(null)
            .commit()
    }
}